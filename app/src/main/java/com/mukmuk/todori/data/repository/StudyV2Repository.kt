package com.mukmuk.todori.data.repository

import com.mukmuk.todori.data.remote.study.MyStudy
import com.mukmuk.todori.data.remote.study.Study
import com.mukmuk.todori.data.remote.study.StudyMember
import com.mukmuk.todori.data.remote.study.StudyTodo
import com.mukmuk.todori.data.remote.study.TodoProgress
import com.mukmuk.todori.data.service.StudyV2Service
import javax.inject.Inject

class StudyV2Repository @Inject constructor(
    private val service: StudyV2Service
) {
    suspend fun getMyStudies(uid: String): List<MyStudy> = service.getMyStudies(uid)

    suspend fun getStudyInfo(studyId: String): Study? = service.getStudyInfo(studyId)

    suspend fun getMembers(studyId: String): List<StudyMember> = service.getMembers(studyId)

    suspend fun getTodos(studyId: String, date: String?): List<StudyTodo> =
        service.getTodos(studyId, date)

    suspend fun getProgressesByStudyAndDate(studyId: String, date: String): List<TodoProgress> =
        service.getProgressesByStudyAndDate(studyId, date)

    // 삽입
    suspend fun insertStudyInfo(study: Study) = service.insertStudyInfo(study)
    suspend fun insertStudyMember(studyId: String, member: StudyMember) = service.insertStudyMember(studyId, member)
    suspend fun insertStudyTodo(todo: StudyTodo) = service.insertStudyTodo(todo)
    suspend fun insertTodoProgress(progress: TodoProgress) = service.insertTodoProgress(progress)

    // StudyV2Repository.kt

    suspend fun getMember(studyId: String, uid: String): StudyMember? =
        service.getMember(studyId, uid)

    suspend fun getTodo(studyId: String, todoId: String): StudyTodo? =
        service.getTodo(studyId, todoId)

    suspend fun getProgress(studyId: String, todoId: String, uid: String): TodoProgress? =
        service.getProgress(studyId, todoId, uid)

    // Repository
    suspend fun insertMyStudy(uid: String, myStudy: MyStudy) =
        service.insertMyStudy(uid, myStudy)

    suspend fun getMyStudy(uid: String, studyId: String): MyStudy? =
        service.getMyStudy(uid, studyId)

}
