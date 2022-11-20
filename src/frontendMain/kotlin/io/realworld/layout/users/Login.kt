package io.realworld.layout.users

import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.View
import io.kvision.core.Container
import io.kvision.core.onEvent
import io.kvision.form.FormPanel
import io.kvision.form.form
import io.kvision.form.formPanel
import io.kvision.form.text.*
import io.kvision.html.ButtonType
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.fieldset
import io.kvision.html.h1
import io.kvision.html.link
import io.kvision.html.p
import io.kvision.html.ul

@kotlinx.serialization.Serializable
data class LoginModel(
    val email: String = "",
    val password: String = ""
)

fun Container.loginPage(state: ConduitState) {
    div(className = "auth-page") {
        div(className = "container page") {
            div(className = "row") {
                div(className = "col-md-6 offset-md-3 col-xs-12") {
                    h1("Sign in", className = "text-xs-center")
                    p(className = "text-xs-center") {
                        link("Need an account?", "#${View.REGISTER.url}")
                    }
                    if (!state.loginErrors.isNullOrEmpty()) {
                        ul(state.loginErrors, className = "error-messages")
                    }

                    formPanel {
                        add(
                            key = LoginModel::email,
                            control = Text(label = "Email", type = TextInputType.EMAIL) {
                                placeholder = "Email"
                            },
                            required = true,
                            requiredMessage = "This field is required",
                            validatorMessage = { "Enter more than 8 characters" }
                        ){ (it.getValue()?.length ?: 0) >= 8 }

                        add(
                            key = LoginModel::password,
                            control = Text(label = "Password", type = TextInputType.PASSWORD) {
                                placeholder = "Password"
                            },
                            required = true,
                            requiredMessage = "This field is required",
                        )

                        button(
                            "Sign in",
                            type = ButtonType.SUBMIT,
                            className = "btn-lg pull-xs-right"
                        )
                    }.onEvent {
                        submit = { ev ->
                            ev.preventDefault()
                            if(this.self.validate()) {
                                this.self.getData().run {
                                    ConduitManager.login(email, password)
                                }
                            }
                        }
                    }

                    /*form {
                        fieldset(className = "form-group") {
                            emailInput =
                                textInput(type = TextInputType.EMAIL, className = "form-control form-control-lg") {
                                    placeholder = "Email"
                                }
                        }
                        fieldset(className = "form-group") {
                            passwordInput =
                                textInput(TextInputType.PASSWORD, className = "form-control form-control-lg") {
                                    placeholder = "Password"
                                }
                        }
                        button(
                            "Sign in",
                            type = ButtonType.SUBMIT,
                            className = "btn-lg pull-xs-right"
                        )
                    }.onEvent {
                        submit = { ev ->
                            ev.preventDefault()
                            ConduitManager.login(emailInput.value, passwordInput.value)
                        }
                    }*/
                }
            }
        }
    }
}
