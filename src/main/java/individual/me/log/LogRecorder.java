package individual.me.log;

public class LogRecorder implements Runnable{

    private final MyLog myLog;
    private final LogMapper logMapper;

    public LogRecorder(MyLog myLog, LogMapper logMapper) {
        this.myLog = myLog;
        this.logMapper = logMapper;
    }

    @Override
    public void run() {
        logMapper.insertLog(myLog);
    }
}
