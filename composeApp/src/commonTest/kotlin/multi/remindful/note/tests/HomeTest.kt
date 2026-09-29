@file:OptIn(ExperimentalTestApi::class)

package multi.remindful.note.tests

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.*
import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kmptmp.composeapp.RemindFulVM
import kmptmp.composeapp.RoomDB.NoteSummary
import kmptmp.composeapp.RoomDB.NotesDAOs
import kmptmp.composeapp.TestTags
import kmptmp.composeapp.UI.Home
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import multi.remindful.note.*
import note.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString
import org.koin.compose.koinInject
import org.koin.test.mock.declare
import kotlin.test.*


class HomeTest : TestWrapper() {

    //Version of @Provide.. interferes with @Module for NoteDao ?
    //@BindValue var mockDaoNotes: NotesDAOs
    @BeforeTest fun setup() =runComposeUiTest {
        /*
        verifySuspend(
            //verify mock calls
            //VerifyMode.x
            //soft - default (check if x() within {} called
            //exhaustive - make sure all funcs called
            //order - funcs called in given order
            //-verifyNoMoreCalls() -- make sure all func usage has been verified else error
            //-resetCalls() -- reset verified funcs
        )
        */

        declare<NotesDAOs> {
            mock<NotesDAOs>(){every { GetAllFlow() }
                .returns( flow { delay(2000L);
                    emit(
                        //artificial delay to enforce init val test
                        //flowOf(
                        listOf(
                            NoteSummary(1L, "0202/02/02 02:02:02", "cc", "tt"),
                            NoteSummary(2L, "0202/02/02 02:02:02", "aa", "zz")
                        )
                    )
                }
            )}
        }
    }

    @Test
    fun TitleDetection() = runComposeUiTest {
        setContent {Home(koinInject<RemindFulVM>().currNotes.collectAsState().value)}

        onNodeWithText( //Find node/function that matches given text
            //stringResource(R.string.Title) no res dynamic
            getString(Res.string.Title)
            , ignoreCase = true
        ).assertIsDisplayed() //Ensure title is displayed!
    }

    //TODO amend turbine tests
    @Test fun testVmInit() = runTest { // Use runTest for coroutine tests
        VMDB.currNotes.test {
            // First emission will be the initialValue (emptyList)
            var emis = awaitItem()
            assertTrue(emis.isEmpty(),"Initial emission should be empty")

            // Second emission will be the mocked data
            emis = awaitItem()
            assertTrue(
                emis.isNotEmpty(),
                "Mocked data emission should not be empty"
            )
        }

    }

    @Test fun testViewModel() = runTest {
        //Arrange
        //uses mock already setup


        //Act
        //.stateIn = needs coroutine to collect actual val

        //.test method to focus purely on the flow/state ? -- from turbine
        VMDB.currNotes.test{
            //var emis = awaitItem();

            assertEquals(emptyList(),awaitItem(), "Fail to get init state!")

            val emis = awaitItem();

            val i2 = emis.last()

            assertTrue( emis.isNotEmpty() )

            //Same = points to same location.. same references
            assertSame(i2,emis.last())
        }
    }

        //TODO DV test comprehensive
    @Test fun DVtest() = runComposeUiTest {
            setContent {Home(koinInject<RemindFulVM>().currNotes.collectAsState().value)}
        //CRule.activity.HVM.SaveNote(NoteInsert("0202/02/02 02:02:02","aa","aa"))

        onNodeWithText(getString(Res.string.vsR)).isDisplayed()

        onNodeWithTag(TestTags.ViewStyle.name)
            .performTouchInput{longClick()}
            .performClick()

        onNodeWithText(getString(Res.string.vsA)).isDisplayed()
    }
}