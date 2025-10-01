package com.mukmuk.todori.ui.screen.todo.list.study

import com.mukmuk.todori.data.remote.study.MyStudy
import com.mukmuk.todori.data.remote.study.Study
import com.mukmuk.todori.data.remote.study.StudyMember
import com.mukmuk.todori.data.remote.study.StudyTodo
import com.mukmuk.todori.data.remote.study.TodoProgress


data class StudyV2ListState(
    val myStudies: List<MyStudy> = emptyList(),
    val studies: Map<String, Study> = emptyMap(),
    val membersMap: Map<String, List<StudyMember>> = emptyMap(),
    val todosMap: Map<String, List<StudyTodo>> = emptyMap(),
    val progressMap: Map<String, Map<String, TodoProgress>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)

