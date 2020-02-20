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

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target, View source) {
    this.target = target;

    target.btn_register = Utils.findRequiredViewAsType(source, R.id.btn_register, "field 'btn_register'", Button.class);
    target.edtUserName = Utils.findRequiredViewAsType(source, R.id.edt_user_name, "field 'edtUserName'", EditText.class);
    target.edtEmail = Utils.findRequiredViewAsType(source, R.id.edt_email, "field 'edtEmail'", EditText.class);
    target.edtPassword = Utils.findRequiredViewAsType(source, R.id.edt_password, "field 'edtPassword'", EditText.class);
    target.edtConfirmPassword = Utils.findRequiredViewAsType(source, R.id.edt_confirm_password, "field 'edtConfirmPassword'", EditText.class);
    target.edtFirstName = Utils.findRequiredViewAsType(source, R.id.edt_first_name, "field 'edtFirstName'", EditText.class);
    target.edtLastName = Utils.findRequiredViewAsType(source, R.id.edt_last_name, "field 'edtLastName'", EditText.class);
    target.edtAddress = Utils.findRequiredViewAsType(source, R.id.edt_address, "field 'edtAddress'", EditText.class);
    target.edtPincode = Utils.findRequiredViewAsType(source, R.id.edt_pincode, "field 'edtPincode'", EditText.class);
    target.edtCity = Utils.findRequiredViewAsType(source, R.id.edt_city, "field 'edtCity'", EditText.class);
    target.edtState = Utils.findRequiredViewAsType(source, R.id.edt_state, "field 'edtState'", EditText.class);
    target.tvSignIn = Utils.findRequiredViewAsType(source, R.id.tv_sign_in, "field 'tvSignIn'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btn_register = null;
    target.edtUserName = null;
    target.edtEmail = null;
    target.edtPassword = null;
    target.edtConfirmPassword = null;
    target.edtFirstName = null;
    target.edtLastName = null;
    target.edtAddress = null;
    target.edtPincode = null;
    target.edtCity = null;
    target.edtState = null;
    target.tvSignIn = null;
  }
}
