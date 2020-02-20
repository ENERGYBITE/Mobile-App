// Generated code from Butter Knife. Do not modify!
package com.ecar.energybite.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ecar.energybite.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.btnSignIn = Utils.findRequiredViewAsType(source, R.id.btn_sign_in, "field 'btnSignIn'", Button.class);
    target.edtUserName = Utils.findRequiredViewAsType(source, R.id.edt_user_name, "field 'edtUserName'", EditText.class);
    target.edtPassword = Utils.findRequiredViewAsType(source, R.id.edt_password, "field 'edtPassword'", EditText.class);
    target.tvSignUp = Utils.findRequiredViewAsType(source, R.id.tv_sign_up, "field 'tvSignUp'", TextView.class);
    target.txvForgotPass = Utils.findRequiredViewAsType(source, R.id.txv_forgot_pass, "field 'txvForgotPass'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnSignIn = null;
    target.edtUserName = null;
    target.edtPassword = null;
    target.tvSignUp = null;
    target.txvForgotPass = null;
  }
}
