@file:OptIn(ExperimentalTestApi::class, ExperimentalTime::class)

package multi.remindful.note.tests

import androidx.compose.ui.test.*
import kmptmp.composeapp.TestTags
import kmptmp.composeapp.UI.NoteEdi
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import multi.remindful.note.TestWrapper
import kotlin.test.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class NoteEdiTest : TestWrapper() {


    @BeforeTest
    override fun setUp(){
        super.setUp()

        //NavVm.navigate(dest= NavRoutes.NoteEditor.name+"/-2")
    }


    //No need to manually test parts of app
    @Test
    fun RemindFragmentTest() = runComposeUiTest{
        setContent { NoteEdi(null) }
        //CRule.setContent { NoteEdi() } //err: already setContent - only use for ComposeView inside

                        //click if visible else throw err!
        //AAA .. Assign , Apply , Assert

                    //Cannot assign based on Text else err oCRuleurs.. as it cannot be clicked if it isnt the same node with given (Text) anymore
        val Rbutton = onNodeWithTag(TestTags.NeRemind.name)
        val Rfrag = onNodeWithTag(TestTags.NeFBg.name)


        Rbutton.assertIsDisplayed().performClick()

                        //make sure its displayed, click, make sure its gone!
        Rfrag.assertIsDisplayed().performClick().assertIsNotDisplayed()

            //Assert the 'remind me!' text doesnt exist anymore but Rbutton isnt hidden i.e. its txt changed
        onNodeWithText("Remind me!").isNotDisplayed()
        Rbutton.isDisplayed()

            //Disable the remind time and check it resets
        Rbutton.performClick().assertTextEquals("Remind me!")
    }

    @Test fun Insert() = runComposeUiTest {
        setContent { NoteEdi(null) }
        // + RTime check

        val dt= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val yr  = dt.year
        val m = dt.month


        onNodeWithTag(TestTags.NeRemind.name).performClick()

        listOf(
            yr,
            yr + 1
        ).forEach {
            onNodeWithText("$m $it").performClick()
            //CRule.onNodeWithContentDescription("[$m $it, Switch to selecting a year]", ignoreCase =  true).performClick();
        }



        onNodeWithTag(TestTags.NeFBg.name).performClick()
        onNodeWithTag(TestTags.NeSave.name).performClick()

        assertTrue(VMDB.currNotes.value.isNotEmpty())

    }
}

