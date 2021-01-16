package com.app.telemed

abstract class EmailFragment : BaseFragment() {

    abstract fun getEmail() : String

    abstract fun setEmail(email: String?)

}
