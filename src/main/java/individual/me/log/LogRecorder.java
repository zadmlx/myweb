package individual.me.log;

import java.util.concurrent.TimeUnit;

public class LogRecorder implements Runnable{

    private MyLog myLog;
    private LogMapper logMapper;

    public LogRecorder(MyLog myLog, LogMapper logMapper) {
        this.myLog = myLog;
        this.logMapper = logMapper;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logMapper.insertLog(myLog);
    }
}
