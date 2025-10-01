package com.mukmuk.todori.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.mukmuk.todori.data.repository.AuthRepository
import com.mukmuk.todori.data.repository.CommunityRepository
import com.mukmuk.todori.data.repository.DailyRecordRepository
import com.mukmuk.todori.data.repository.GoalRepository
import com.mukmuk.todori.data.repository.GoalStatsRepository
import com.mukmuk.todori.data.repository.GoalTodoRepository
import com.mukmuk.todori.data.repository.HomeRepository
import com.mukmuk.todori.data.repository.QuestRepository
import com.mukmuk.todori.data.repository.StudyRepository
import com.mukmuk.todori.data.repository.StudyStatsRepository
import com.mukmuk.todori.data.repository.StudyV2Repository
import com.mukmuk.todori.data.repository.TodoCategoryRepository
import com.mukmuk.todori.data.repository.TodoRepository
import com.mukmuk.todori.data.repository.TodoStatsRepository
import com.mukmuk.todori.data.repository.UserRepository
import com.mukmuk.todori.data.service.CommunityService
import com.mukmuk.todori.data.service.DailyRecordService
import com.mukmuk.todori.data.service.GoalService
import com.mukmuk.todori.data.service.GoalTodoService
import com.mukmuk.todori.data.service.HomeService
import com.mukmuk.todori.data.service.QuestService
import com.mukmuk.todori.data.service.StudyService
import com.mukmuk.todori.data.service.StudyV2Service
import com.mukmuk.todori.data.service.TodoCategoryService
import com.mukmuk.todori.data.service.TodoService
import com.mukmuk.todori.data.service.TodoStatsService
import com.mukmuk.todori.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@RequiresApi(Build.VERSION_CODES.O)
object AppModule {


    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideTodoCategoryService(
        firestore: FirebaseFirestore
    ): TodoCategoryService = TodoCategoryService(firestore)

    @Provides
    @Singleton
    fun provideTodoCategoryRepository(
        todoCategoryService: TodoCategoryService
    ): TodoCategoryRepository = TodoCategoryRepository(todoCategoryService)

    @Provides
    @Singleton
    fun provideStudyService(
        firestore: FirebaseFirestore
    ): StudyService = StudyService(firestore)

    @Provides
    @Singleton
    fun provideStudyRepository(
        studyService: StudyService
    ): StudyRepository = StudyRepository(studyService)

    // MyPageProfile
    @Provides
    @Singleton
    fun provideUserService(
        firestore: FirebaseFirestore
    ): UserService = UserService(firestore)

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService
    ): UserRepository = UserRepository(userService)
    
    @Provides
    @Singleton
    fun provideGoalService(firestore: FirebaseFirestore): GoalService =
        GoalService(firestore)

    @Provides
    @Singleton
    fun provideGoalRepository(goalService: GoalService): GoalRepository =
        GoalRepository(goalService)

    @Provides
    @Singleton
    fun provideQuestService(
        firestore: FirebaseFirestore
    ): QuestService = QuestService(firestore)

    @Provides
    @Singleton
    fun provideQuestRepository(
        questService: QuestService,
        firestore: FirebaseFirestore
    ): QuestRepository = QuestRepository(questService, firestore)

    @Provides
    @Singleton
    fun provideTodoService(firestore: FirebaseFirestore): TodoService =
        TodoService(firestore)

    @Provides
    @Singleton
    fun provideTodoRepository(todoService: TodoService): TodoRepository =
        TodoRepository(todoService)

    @Provides
    @Singleton
    fun provideCommunityService(
        firestore: FirebaseFirestore
    ): CommunityService = CommunityService(firestore)

    @Provides
    @Singleton
    fun provideCommunityRepository(
        communityService: CommunityService
    ): CommunityRepository = CommunityRepository(communityService)

    @Provides
    @Singleton
    fun provideGoalTodoService(firestore: FirebaseFirestore): GoalTodoService =
        GoalTodoService(firestore)

    @Provides
    @Singleton
    fun provideGoalTodoRepository(goalTodoService: GoalTodoService): GoalTodoRepository =
        GoalTodoRepository(goalTodoService)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFunctions(): FirebaseFunctions = FirebaseFunctions.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepository(auth)

    @Provides
    @Singleton
    fun provideDailyRecordService(firestore: FirebaseFirestore): DailyRecordService =
        DailyRecordService(firestore)

    @Provides
    @Singleton
    fun provideDailyRecordRepository(dailyRecordService: DailyRecordService): DailyRecordRepository =
        DailyRecordRepository(dailyRecordService)

    @Provides
    @Singleton
    fun provideTodoStatsService(firestore: FirebaseFirestore): TodoStatsService =
        TodoStatsService(firestore)

    @Provides
    @Singleton
    fun provideTodoStatsRepository(todoStatsService: TodoStatsService): TodoStatsRepository =
        TodoStatsRepository(todoStatsService)

    @Provides
    @Singleton
    fun provideGoalStatsRepository(goalService: GoalService): GoalStatsRepository =
        GoalStatsRepository(goalService)

    @Provides
    @Singleton
    fun provideStudyStatsRepository(studyService: StudyService): StudyStatsRepository =
        StudyStatsRepository(studyService)

    @Provides
    @Singleton
    fun provideHomeService(firestore: FirebaseFirestore): HomeService =
        HomeService(firestore)

    @Provides
    @Singleton
    fun provideHomeRepository(homeService: HomeService): HomeRepository =
        HomeRepository(homeService)

    @Provides
    @Singleton
    fun provideStudyV2Service(firestore: FirebaseFirestore): StudyV2Service =
        StudyV2Service(firestore)

    @Provides
    @Singleton
    fun provideStudyV2Repository(studyV2Service: StudyV2Service): StudyV2Repository =
        StudyV2Repository(studyV2Service)

}
