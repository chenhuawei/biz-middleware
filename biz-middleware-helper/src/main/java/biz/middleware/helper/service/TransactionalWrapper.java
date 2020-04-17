package biz.middleware.helper.service;

import biz.middleware.helper.function.SimpleFunction;

public interface TransactionalWrapper {
    void execute(SimpleFunction function);
}
