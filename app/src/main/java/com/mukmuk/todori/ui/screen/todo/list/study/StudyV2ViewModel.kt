package com.mukmuk.todori.ui.screen.todo.list.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukmuk.todori.data.repository.StudyV2Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyV2ListViewModel @Inject constructor(
    private val repo: StudyV2Repository
) : ViewModel() {

    private val _state = MutableStateFlow(StudyV2ListState())
    val state: StateFlow<StudyV2ListState> = _state

//    fun loadAllStudies(uid: String, date: String? = null) {
//        viewModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true)
//            try {
//                val start = System.currentTimeMillis()
//                val myStart = System.currentTimeMillis()
//                val myStudies = repo.getMyStudies(uid)
//                val myEnd = System.currentTimeMillis()
//                println("StudyV2ListViewModel ⏱ getMyStudies = ${myEnd - myStart} ms")
//
//                println("StudyV2ListViewModel myStudies: $myStudies")
//                println("StudyV2ListViewModel date: $uid")
//                val studyIds = myStudies.map { it.studyId }
//
//                val studiesDeferred = async(Dispatchers.IO) {
//                    val s = System.currentTimeMillis()
//                    val result = studyIds.mapNotNull { id ->
//                        println("StudyV2ListViewModel ▶ getStudyInfo START $id, thread=${Thread.currentThread().name}")
//                        repo.getStudyInfo(id)
//                    }
//                    val e = System.currentTimeMillis()
//                    println("StudyV2ListViewModel ⏱ getStudyInfo total = ${e - s} ms")
//                    result
//                }
//
//                val membersDeferred = async(Dispatchers.IO) {
//                    val s = System.currentTimeMillis()
//                    val result = studyIds.flatMap { id ->
//                        println("StudyV2ListViewModel ▶ getMembers START $id, thread=${Thread.currentThread().name}")
//                        repo.getMembers(id)
//                    }
//                    val e = System.currentTimeMillis()
//                    println("StudyV2ListViewModel ⏱ getMembers total = ${e - s} ms")
//                    result
//                }
//
//                val todosDeferred = async(Dispatchers.IO) {
//                    val s = System.currentTimeMillis()
//                    val result = studyIds.flatMap { id ->
//                        println("StudyV2ListViewModel ▶ getTodos START $id, thread=${Thread.currentThread().name}")
//                        repo.getTodos(id, date)
//                    }
//                    val e = System.currentTimeMillis()
//                    println("StudyV2ListViewModel ⏱ getTodos total = ${e - s} ms")
//                    result
//                }
//
//                val progressesDeferred = async(Dispatchers.IO) {
//                    val s = System.currentTimeMillis()
//                    val result = studyIds.flatMap { id ->
//                        println("StudyV2ListViewModel ▶ getProgressesByStudyAndDate START $id, thread=${Thread.currentThread().name}")
//                        repo.getProgressesByStudyAndDate(id, date ?: "")
//                    }
//                    val e = System.currentTimeMillis()
//                    println("StudyV2ListViewModel ⏱ getProgressesByStudyAndDate total = ${e - s} ms")
//                    result
//                }
//
//                val studies = studiesDeferred.await()
//                val members = membersDeferred.await()
//                val todos = todosDeferred.await()
//                val progresses = progressesDeferred.await()
//
//                val end = System.currentTimeMillis()
//                println("StudyV2ListViewModel ⏱ loadAllStudies total = ${end - start} ms")
//
//                println("StudyV2ListViewModel studies: $studies")
//                println("StudyV2ListViewModel members: $members")
//                println("StudyV2ListViewModel todos: $todos")
//                println("StudyV2ListViewModel progresses: $progresses")
//
//                // Map 가공
//                val studiesMap = studies.associateBy { it.studyId }
//                val membersMap = members.groupBy { it.studyId }
//                val todosMap = todos.groupBy { it.studyId }
//                val progressMap = progresses.groupBy { it.studyId }
//                    .mapValues { entry ->
//                        entry.value.associateBy { it.studyTodoId }
//                    }
//
//                _state.value = _state.value.copy(
//                    myStudies = myStudies,
//                    studies = studiesMap,
//                    membersMap = membersMap,
//                    todosMap = todosMap,
//                    progressMap = progressMap,
//                    isLoading = false,
//                    error = null
//                )
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(error = e.message, isLoading = false)
//            }
//        }
//    }


    fun loadAllStudies(uid: String, date: String? = null) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val start = System.currentTimeMillis() // 전체 시작 시간

                // 1. 내 스터디 목록 가져오기
                val myStart = System.currentTimeMillis()
                val myStudies = repo.getMyStudies(uid)
                val myEnd = System.currentTimeMillis()
                println("StudyV2ListViewModel ⏱ getMyStudies = ${myEnd - myStart} ms")

                val studyIds = myStudies.map { it.studyId }

                // 2. studyIds × 각 쿼리를 병렬로 실행
                val studyInfos = studyIds.map { id ->
                    async(Dispatchers.IO) {
                        val s = System.currentTimeMillis()
                        println("StudyV2ListViewModel ▶ getStudyInfo START $id, thread=${Thread.currentThread().name}")
                        val result = repo.getStudyInfo(id)
                        val e = System.currentTimeMillis()
                        println("StudyV2ListViewModel ⏱ getStudyInfo $id = ${e - s} ms")
                        result
                    }
                }

                val studyMembers = studyIds.map { id ->
                    async(Dispatchers.IO) {
                        val s = System.currentTimeMillis()
                        println("StudyV2ListViewModel ▶ getMembers START $id, thread=${Thread.currentThread().name}")
                        val result = repo.getMembers(id)
                        val e = System.currentTimeMillis()
                        println("StudyV2ListViewModel ⏱ getMembers $id = ${e - s} ms")
                        result
                    }
                }

                val studyTodos = studyIds.map { id ->
                    async(Dispatchers.IO) {
                        val s = System.currentTimeMillis()
                        println("StudyV2ListViewModel ▶ getTodos START $id, thread=${Thread.currentThread().name}")
                        val result = repo.getTodos(id, date)
                        val e = System.currentTimeMillis()
                        println("StudyV2ListViewModel ⏱ getTodos $id = ${e - s} ms")
                        result
                    }
                }

                val studyProgresses = studyIds.map { id ->
                    async(Dispatchers.IO) {
                        val s = System.currentTimeMillis()
                        println("StudyV2ListViewModel ▶ getProgresses START $id, thread=${Thread.currentThread().name}")
                        val result = repo.getProgressesByStudyAndDate(id, date ?: "")
                        val e = System.currentTimeMillis()
                        println("StudyV2ListViewModel ⏱ getProgresses $id = ${e - s} ms")
                        result
                    }
                }

                // 3. 결과 모으기
                val studies = studyInfos.mapNotNull { it.await() }
                val members = studyMembers.flatMap { it.await() }
                val todos = studyTodos.flatMap { it.await() }
                val progresses = studyProgresses.flatMap { it.await() }

                val end = System.currentTimeMillis()
                println("StudyV2ListViewModel ⏱ loadAllStudies total = ${end - start} ms")

                // 4. Map 가공
                val studiesMap = studies.associateBy { it.studyId }
                val membersMap = members.groupBy { it.studyId }
                val todosMap = todos.groupBy { it.studyId }
                val progressMap = progresses.groupBy { it.studyId }
                    .mapValues { entry ->
                        entry.value.associateBy { it.studyTodoId }
                    }

                // 5. State 업데이트
                _state.value = _state.value.copy(
                    myStudies = myStudies,
                    studies = studiesMap,
                    membersMap = membersMap,
                    todosMap = todosMap,
                    progressMap = progressMap,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }


}
