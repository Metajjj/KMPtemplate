package kmptmp.composeapp.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

//1 Entity = 1 Table

fun DTFormat(time: String) : Boolean{
    try {
        //println("ToFormat: $time")

        LocalDateTime
            .Format {
                //D/a/t/e t:i:m:e
                year(); char('/'); monthNumber(); char('/'); day();
                char(' ')
                hour(); char(':'); minute(); char(':'); second();
            }.parse(time);
    } catch (e: Exception){
        println("ERR :$e")
        return false;
    }
    return true;
}

//Same init constructor - super class to make them all follow it!

//abstract instead of open as I dont want it used
abstract class SuperNoteFormat(vararg T:String?){
    //For formatting the time strings
    init{
        require(
            T.all { s -> s == null || DTFormat(s) }
        )
    }
}


//@Fts4 //Full text-search within SQLITE querys?? sqlite extension ??

//@AutoValue - Java-based ROOM turns the class Longo a data class like kotlin
    //Can add annotation near col or setup in Entity as array
@Entity(
    //tableName = "2"

    //primaryKeys=[] //Composite PK i.e. [Title, Content]
    //ignoredColumns =["InheritedCol"] //Can use this as alternative and specify parent cols
    indices = [Index(value=["Title","Content"], unique = true)],
                //Force all rows to have Unique Title+Content

)
data class NoteEntity(
    //@PrimaryKey val ID: ULong, //Redundant when LastUpdate will always be unique?
    //ERR: doesn't like ULongs

        //SQLITE INTEGER is LONG
    @PrimaryKey(autoGenerate = false) val ID:Long,
    @ColumnInfo(name="YMDHMS") val LUpdateTime : String,
    @ColumnInfo() val Title: String?=null,
    @ColumnInfo() val Content: String?=null,
    @ColumnInfo(name="R_Time") val RemindTime : String?=null,
    //@Ignore val img: Bitmap //Exclude this col from DB
) : SuperNoteFormat(LUpdateTime,RemindTime)


/*
//Indented to show its an alternative for the class above
    //For Inserts i.e. no ID
data class NoteInsert(
    @ColumnInfo(name="YMDHMS") val LUTime: String,
    val Title: String?=null,
    @ColumnInfo(name="Content") val Text: String?=null,
    @ColumnInfo(name="R_Time") val RemindTime : String?=null,
) : SuperNoteFormat(LUTime)*/

    //Simple holder to auto-filtrate results
    //Will auto-map based on name, can fallback to @ColInfo for matches
    data class NoteSummary(
        @PrimaryKey(autoGenerate = false) val ID:Long,
        @ColumnInfo(name="YMDHMS") val LUTime: String,
        val Title: String?=null,
        @ColumnInfo(name="Content") val Text: String?=null,
    ) : SuperNoteFormat(LUTime)