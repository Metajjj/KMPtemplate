
package multi.remindful.note.tests

//Cannot do DB tests in commonTest
/*
import androidx.room.Room

import multi.remindful.note.TestWrapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import multi.remindful.note.RoomDB.RemindFulDB

import org.koin.core.component.get
import org.koin.test.mock.declare
import kotlin.test.BeforeTest
import kotlin.test.Test


//HiltTestApplication used auto by hilt??

class DbTest : TestWrapper(){

        //Is purely DB related - doesn't need the android device -- shift into test folders?

    //@BindValue // modify and give to hilt
    //Receive from hilt
    //@Inject lateinit var TestDB : RemindFulDB;
    lateinit var TestDB : RemindFulDB;

    @BeforeTest
    override fun setUp() {
        super.setUp()

        declare<RemindFulDB>{
            Room.inMemoryDatabaseBuilder(get<Context>(), RemindFulDB::class.java)
                .build()
        }

        TestDB = get();

    }

    //Tests
    @Test
    fun isMemDB(){
        Assert.assertNull(
            "DB is not memory!",
            TestDB.openHelper.databaseName
        )

        runBlocking {
            TestDB.NotesDao().insert(NoteEntity(-1, "0202/02/02 02:02:02", "t", "c"))
        }
    }
    @Test fun memDBCRUD(){
        Assert.assertFalse("DB should be closed with no CRUD ops made",TestDB.isOpen)


        runBlocking {
            with(Dispatchers.IO) {
                TestDB.NotesDao().Insert(NoteEntity(1, "0202/02/02 02:02:02", "a", "a"))
                Assert.assertTrue(TestDB.NotesDao().GetAll().isNotEmpty())
            }
        }


        Assert.assertTrue(TestDB.isOpen) //Should be in open-state from CRUD ops
        //Insert data
    }
    @Test fun memDBStartsEmpty(){
            //Should fail if memory, wont persist per test
        runBlocking {
            with(Dispatchers.IO) {
                val o = TestDB.NotesDao().GetAll();
                Assert.assertFalse("$o", o.isNotEmpty() )
            }
        }
    }
}

*/
