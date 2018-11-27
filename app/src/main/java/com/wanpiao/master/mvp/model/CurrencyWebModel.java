package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.CurrencyWebContract;

import javax.inject.Inject;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
public class CurrencyWebModel implements CurrencyWebContract.Model {

    @Override
    public void onDestroy() {

    }

    @Inject
    public CurrencyWebModel() {

    }
}
