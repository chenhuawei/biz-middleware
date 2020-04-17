package biz.middleware.helper.service.impl;

import biz.middleware.helper.function.SimpleFunction;
import biz.middleware.helper.service.TransactionalWrapper;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalWrapperImpl implements TransactionalWrapper {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(SimpleFunction function) {
        function.execute();
    }
}
