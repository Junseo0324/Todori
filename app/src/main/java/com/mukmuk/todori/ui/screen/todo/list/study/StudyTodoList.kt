package com.mukmuk.todori.ui.screen.todo.list.study

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.mukmuk.todori.ui.screen.todo.component.StudyCard
import com.mukmuk.todori.ui.theme.AppTextStyle
import com.mukmuk.todori.ui.theme.Dimens
import com.mukmuk.todori.util.toKoreanString
import kotlinx.datetime.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudyTodoList(selectedDate: LocalDate,navController: NavHostController) {
//    val viewModel: StudyListViewModel = hiltViewModel()
    val viewModel: StudyV2ListViewModel = hiltViewModel()

    val uid = Firebase.auth.currentUser?.uid.toString()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(selectedDate) {
        viewModel.loadAllStudies(uid, selectedDate.toString())
        println("selectedDate: $selectedDate")
        println("loadAllStudies todosMap: ${state.todosMap}")
        println("loadAllStudies studies: ${state.studies}")
        println("loadAllStudies membersMap: ${state.membersMap}")
        println("loadAllStudies progressMap: ${state.progressMap}")
    }
    val studies = state.studies.values.toList()
    val membersMap = state.membersMap
    val todosMap = state.todosMap
    val myProgressMap = state.progressMap
    val selectedDayOfWeek = selectedDate.dayOfWeek.toKoreanString()
    val filteredStudies = studies.filter { study ->
        study.activeDays.contains(selectedDayOfWeek)
    }

    if (filteredStudies.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircleOutline,
                contentDescription = "Study Empty",
                modifier = Modifier.size(38.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(Dimens.Tiny))
            Text(
                "참여 중인 스터디가 없습니다",
                style = AppTextStyle.BodyLarge,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(Dimens.Tiny))
            Text(
                "직접 스터디를 만들고 커뮤니티에서 팀원을 모집해 보세요!",
                style = AppTextStyle.BodySmall,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(Dimens.Medium))
        }
    } else {
        LazyColumn {
            items(studies.size) { index ->
                val study = studies[index]
                val members = membersMap[study.studyId].orEmpty()
                val todos = todosMap[study.studyId].orEmpty()
                val progresses = myProgressMap[study.studyId].orEmpty()
                study.activeDays
                StudyCard(
                    study = study,
                    studyTodos =  todos,
                    myProgressMap = progresses,
                    memberCount = members.size,
                    joinedAt = members.firstOrNull { it.uid == uid }?.joinedAt ?: Timestamp.now(),
                    selectedDate = selectedDate,
                    onClick = {
                        navController.navigate("study/detail/${study.studyId}?date=${selectedDate}")
                    }
                )
            }
        }
    }
}
