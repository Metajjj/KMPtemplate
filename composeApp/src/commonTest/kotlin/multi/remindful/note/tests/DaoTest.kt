package multi.remindful.note.tests

import dev.mokkery.answering.calls
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kmptmp.composeapp.RoomDB.NoteEntity
import kmptmp.composeapp.RoomDB.NotesDAOs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import multi.remindful.note.TestWrapper
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals


class DaoTest : TestWrapper(){
    //Rules/Cntrls
    //Is purely DB related - doesn't need the android device -- shift into test folders?
    //@get:Rule(order=0) val HiltRule = HiltAndroidRule(this); //needs "this" to ref class at runtime

    //@Inject lateinit var Ndao : NotesDAOs
    //Inject = receive | BindValue = set
    //@Inject lateinit var Ndao : NotesDAOs// = getKoin().get()


    @Test fun DaoMockCrud(){
        val Ndao = get<NotesDAOs>();


        runBlocking {  with(Dispatchers.IO) {
            assertEquals(1, Ndao.genID())
            kotlin.test.assertFailsWith(
                Exception::class,
                {
                    runBlocking{ Ndao.insert(NoteEntity(1L,""))}
                })
            Ndao.Insert(NoteEntity(1, "0202/02/02 02:02:02"))
        }}
    }
        //has to be open to mock and return args i.e. .answers{}
    abstract class m{open fun a(A:Any?)=A}
    @Test fun DaoMockTypeCheck(){

        //Can test for types and vals
        val mM = mock<m>(){
            /*
                //later params take precedence over prior params!
            every{ a( any() ) }.answers {  args.firstOrNull() }
            //ofType<>() = type | eq() = val || .answers = change func body
            every { a( ofType<String>() ) }.returns("String")
            every { a(eq(1) ) }.returns("Int")
            //generic - overrides all others!
            //every { a( any() ) }.answers { args.firstOrNull() }

            */

            //Simplifying into 1 method
            every{a( any() )}.calls {
                val p1 = it.args.firstOrNull()
                when(p1){
                    is String -> "String"
                    1 -> "Int"
                    else -> p1
                }
            }
            /* if 2+ params
            when{
                p1 is String && p2 is String -> {"Both Strings!!"}
                else -> {"TO DO"}
            */

        }

        assertEquals("String", mM.a(""), message = "String test");
        assertEquals("Int",mM.a(1), "1 test")
        assertEquals(0,mM.a(0), "Int test")
        assertEquals(1.4f, mM.a(1.4f), "Float test")


    }
}