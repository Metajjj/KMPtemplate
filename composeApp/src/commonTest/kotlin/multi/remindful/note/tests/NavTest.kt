package multi.remindful.note.tests


import kmptmp.composeapp.UI.NavRoutes
import multi.remindful.note.TestWrapper
import kotlin.test.Test


class NavTest : TestWrapper() {

    @Test fun NavigateTest(){
        NavVm.navigate(dest= NavRoutes.Landing.name)
    }

}