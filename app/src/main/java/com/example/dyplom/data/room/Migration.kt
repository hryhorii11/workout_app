import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "drop table exercises;Create table \"exercises\" (\n" +
                "  \"id\"  integer primary key,\n" +
                "  \"name\" text not null,\n" +
                "  \"category\" text not null,\n" +
                "  \"description\" text,\n" +
                "  \"image\" text);")
    }
}