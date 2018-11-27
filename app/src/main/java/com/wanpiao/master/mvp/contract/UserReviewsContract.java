package com.wanpiao.master.mvp.contract;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/21      onCreate
 */
public interface UserReviewsContract {

    interface View extends IView {
        void showCommentState(String response);
        void showVoliceCommentState(String response);
    }

    interface Presenter extends IPresenter {
        void callComments(String comType , String userId , String JoinId , String comment , int comScore);
        void callVoiceComments(String comType , String userId , String JoinId , String comment , int comScore , String commentVoice , Double voiceLength);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> sendComments(String comType , String userId , String JoinId , String comment , int comScore );
        Observable<BaseEntity<String>> sendVoiceComments(String comType , String userId , String JoinId , String comment , int comScore , String commentVoice , Double voiceLength );
    }

}
