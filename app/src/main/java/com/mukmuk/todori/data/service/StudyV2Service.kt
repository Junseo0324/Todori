package com.mukmuk.todori.data.service

import com.google.firebase.firestore.FirebaseFirestore
import com.mukmuk.todori.data.remote.study.MyStudy
import com.mukmuk.todori.data.remote.study.Study
import com.mukmuk.todori.data.remote.study.StudyMember
import com.mukmuk.todori.data.remote.study.StudyTodo
import com.mukmuk.todori.data.remote.study.TodoProgress
import kotlinx.coroutines.tasks.await

class StudyV2Service(
    private val firestore: FirebaseFirestore
) {
    // [내 스터디 목록 조회] users/{uid}/myStudies/*
    suspend fun getMyStudies(uid: String): List<MyStudy> {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("myStudies")
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(MyStudy::class.java) }
    }

    // [스터디 정보 조회] studies/{studyId}/info/meta
    suspend fun getStudyInfo(studyId: String): Study? {
        val snapshot = firestore.collection("studies")
            .document(studyId)
            .collection("info")
            .document("meta")
            .get()
            .await()
        return snapshot.toObject(Study::class.java)
    }

    // [스터디 멤버 조회] studies/{studyId}/members/*
    suspend fun getMembers(studyId: String): List<StudyMember> {
        val snapshot = firestore.collection("studies")
            .document(studyId)
            .collection("members")
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(StudyMember::class.java) }
    }

    // [스터디 todos 조회] studies/{studyId}/todos/*
    suspend fun getTodos(studyId: String, date: String? = null): List<StudyTodo> {
        val ref = firestore.collection("studies")
            .document(studyId)
            .collection("todos")
        val snapshot = if (date != null) {
            ref.whereEqualTo("date", date).get().await()
        } else {
            ref.get().await()
        }
        return snapshot.documents.mapNotNull { it.toObject(StudyTodo::class.java) }
    }

    // [스터디 전체 진행상황 조회] collectionGroup(progress)
    suspend fun getProgressesByStudyAndDate(studyId: String, date: String): List<TodoProgress> {
        val snapshot = firestore.collectionGroup("progress")
            .whereEqualTo("studyId", studyId)
            .whereEqualTo("date", date)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(TodoProgress::class.java) }
    }

    suspend fun insertStudyInfo(study: Study) {
        firestore.collection("studies")
            .document(study.studyId)
            .collection("info")
            .document("meta")
            .set(study)
            .await()
    }

    suspend fun insertStudyMember(studyId: String, member: StudyMember) {
        firestore.collection("studies")
            .document(studyId)
            .collection("members")
            .document(member.uid)
            .set(member)
            .await()
    }

    suspend fun insertStudyTodo(todo: StudyTodo) {
        firestore.collection("studies")
            .document(todo.studyId)
            .collection("todos")
            .document(todo.studyTodoId)
            .set(todo)
            .await()
    }

    suspend fun insertTodoProgress(progress: TodoProgress) {
        firestore.collection("studies")
            .document(progress.studyId)
            .collection("todos")
            .document(progress.studyTodoId)
            .collection("progress")
            .document(progress.uid)
            .set(progress)
            .await()
    }

    // StudyV2Service.kt

    // [스터디 멤버 단일 조회]
    suspend fun getMember(studyId: String, uid: String): StudyMember? {
        val snapshot = firestore.collection("studies")
            .document(studyId)
            .collection("members")
            .document(uid)
            .get()
            .await()
        return snapshot.toObject(StudyMember::class.java)
    }

    // [스터디 todo 단일 조회]
    suspend fun getTodo(studyId: String, todoId: String): StudyTodo? {
        val snapshot = firestore.collection("studies")
            .document(studyId)
            .collection("todos")
            .document(todoId)
            .get()
            .await()
        return snapshot.toObject(StudyTodo::class.java)
    }

    // [특정 todo 의 progress 단일 조회]
    suspend fun getProgress(studyId: String, todoId: String, uid: String): TodoProgress? {
        val snapshot = firestore.collection("studies")
            .document(studyId)
            .collection("todos")
            .document(todoId)
            .collection("progress")
            .document(uid)
            .get()
            .await()
        return snapshot.toObject(TodoProgress::class.java)
    }


    suspend fun insertMyStudy(uid: String, myStudy: MyStudy) {
        firestore.collection("users")
            .document(uid)
            .collection("myStudies")
            .document(myStudy.studyId)
            .set(myStudy)
            .await()
    }

    suspend fun getMyStudy(uid: String, studyId: String): MyStudy? {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("myStudies")
            .document(studyId)
            .get()
            .await()
        return snapshot.toObject(MyStudy::class.java)
    }
}
