package com.payneteasy.srvlog.service.impl;

import com.payneteasy.srvlog.dao.ILogDao;
import com.payneteasy.srvlog.data.LogData;
import com.payneteasy.srvlog.service.ILogCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Date: 05.01.13
 */
@Transactional
@Service
public class SimpleLogCollector implements ILogCollector {

    @Autowired
    private ILogDao logDao;

    @Override
    public void saveLog(LogData logData) {
        logDao.saveLog(logData);
    }

    public void setLogDao(ILogDao logDao) {
        this.logDao = logDao;
    }
}