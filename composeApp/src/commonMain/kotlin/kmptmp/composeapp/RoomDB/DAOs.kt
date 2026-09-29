package kmptmp.composeapp.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


//DAO - data access object -> query holder to access DB

    //Err: cannot use generic - has to be compile-time friendly
@Dao
interface NotesDAOs{

    //===========HELPER FUNCTIONS========
    suspend fun genID():Long{
        var i = 1L;

        for(n in GetAll().sortedBy{/*n->n*/it.ID})
            if(i != n.ID) return i
            else ++i

        return i;
    }

    //===========HELPER FUNCTIONS========







    /*
     Kotlin - suspend keyword to force it asynchronous - to not block UI thread
      return LiveData/Flow<> so that any DB change is reflected automatically

      --innately suspend as err on Main thread!
    */

    //Inserts the given notes into DB
            //Use for update - Optional fallback
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Default ABORT i.e. cancel transaction
	suspend fun insert(note: NoteEntity)
    suspend fun Insert(note: NoteEntity){
        //Generate ID - autoGen is off!
        insert( note.copy(genID()) );
    }
    /*{
        notes = notes.map { notes ->  notes.copy(
            LUpdateTime = RemindFulDB.getDB(c).. filter...
        ) }
            //vararg = Array<out K> .. * -> get OUT val
        //Insert(*notes) //Run into actual @Insert
    }*/
    //@Insert fun Insert2(u:User, friends: List<User>) //Alt insert method

    //Delete any num of notes - auto targets the DB/table its attached to
    @Delete
	suspend fun Delete(vararg note: NoteEntity)

    @Update()
	suspend fun Update(note:NoteEntity); //Only accept 1 update at a time

        //Auto references from existing code and highlights it
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM NoteEntity")
	suspend fun GetAll() : List<NoteEntity> //Returns the Entity(s) found in a List format

    //Returns Flow<> as auto-updater where-ever its needed
        //Tell it to drop unused Cols
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM NoteEntity")
    fun GetAllFlow() : Flow<List<NoteSummary>>
        //Err: not allowed to be suspend as Flow needs to always update regardless of where data is stored

                                 //Can use : to target params
    @Query("SELECT * FROM NoteEntity WHERE ID IN (:i) ;")
	suspend fun GetByIds(vararg i:Long) : List<NoteEntity> //LiveData<List<NoteEntity>>

    @Query("DELETE FROM NoteEntity WHERE ID IN (:i)")
    suspend fun DelByIds(vararg i:Long) : Int //returns rows affected as SQLITE


        @Query("SELECT * FROM NoteEntity WHERE YMDHMS IN (:i) ;")
        suspend fun GetByLUT(vararg i:String) : List<NoteEntity>

    /*
        //Use Map type to show relations between joined tables
        //Map annotation to filter automatically
    @MapInfo(keyColumn = "userName", valueColumn = "bookName")
    @Query(
        "SELECT user.name AS username, book.name AS bookname FROM user" +
        "JOIN book ON user.id = book.user_id"
    )
	suspend fun loadUserAndBookNames(): Map<String, List<String>>
    */
}