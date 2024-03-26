package individual.me.log;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogMapper {

    public void insertLog(@Param("mylog") MyLog log);

    void deleteLog(@Param("id") int id);

    List<MyLog> selectLog();
}
