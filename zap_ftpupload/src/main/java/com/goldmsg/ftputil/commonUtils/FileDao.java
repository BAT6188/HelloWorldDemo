package com.goldmsg.ftputil.commonUtils;

import java.sql.SQLException;
import java.util.List;

public class FileDao {
    public static String SQL_QUERY_FILE =
            "SELECT " +
                    "file_id," +
                    "device_id," +
                    "file_name," +
                    "police_id," +
                    "DATE_FORMAT(import_time,'%Y-%m-%d %H:%i:%s') `import_time`," +
                    "DATE_FORMAT(capture_time,'%Y-%m-%d %H:%i:%s') `capture_time`," +
                    "type," +
                    "size," +
                    "path," +
                    "play_path," +
                    "duration," +
                    "thumbnail," +
                    "status," +
                    "trans_status," +
                    "code_rate," +
                    "frame_rate," +
                    "lateral_resolution," +
                    "vertical_resolution," +
                    "code_format," +
                    "is_keyfile," +
                    "is_sync" +
                    " FROM " +
                    "ws_file" +
                    " WHERE file_id=?";

    /**
     * @param userFileId
     * @return
     */
    public List queryFile(String userFileId) throws SQLException {
        List result = null;
        result = DBUtils.executeQuery(SQL_QUERY_FILE, userFileId);
        return result;
    }

}
