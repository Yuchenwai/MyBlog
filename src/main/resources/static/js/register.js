const regUsername = /^[a-zA-z0-9_]{5,10}$/;
const regEmail = /^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\.[a-zA-Z0-9]+)+$/;
const regPassword = /^[a-zA-Z0-9_]{5,18}$/;
const formUsername = $("input[name='username']");
const formEmail = $("input[name='email']");
const formPassword = $("input[name='password']");
const formPasswords = $("#passwords");
let flagUsername = false;
let flagEmail = false;
let flagPassword = false;
let flagPasswords = false;
formUsername.blur(function () {
    const username = formUsername.val().trim();
    if (username.length > 0) {
        if (regUsername.test(username)) {
            justUsername(username)
        } else {
            $("#usernameFeedback").html("请输入长度为 5 - 10 位的用户名(字母、数组或下划线)");
            formUsername.removeClass("is-valid");
            formUsername.addClass("is-invalid");
            flagUsername = false
        }
    }
});
formEmail.blur(function () {
    const email = formEmail.val().trim();
    if (email.length > 0) {
        if (regEmail.test(email)) {
            justEmail(email)
        } else {
            $("#emailFeedback").html("请输入合法的邮箱");
            formEmail.removeClass("is-valid");
            formEmail.addClass("is-invalid");
            flagEmail = false
        }
    }
});

formPassword.blur(function () {
    const password = formPassword.val().trim();
    if (password.length > 0) {
        if (regPassword.test(password)) {
            formPassword.removeClass("is-invalid");
            formPassword.addClass("is-valid");
            flagPassword = true
        } else {
            formPassword.removeClass("is-valid");
            formPassword.addClass("is-invalid");
            flagPassword = false
        }
    }
});
formPasswords.blur(function () {
    const passwords = formPasswords.val().trim();
    if (passwords.length > 0 && flagPassword) {
        if (formPassword.val() === passwords) {
            formPasswords.removeClass("is-invalid");
            formPasswords.addClass("is-valid");
            flagPasswords = true
        } else {
            formPasswords.removeClass("is-valid");
            formPasswords.addClass("is-invalid");
            flagPasswords = false
        }
    }
});
$(".my-submit").click(function () {
    submitForm()
});
$("input").keydown(function (a) {
    if (a.keyCode === 13) {
        formUsername.blur();
        formEmail.blur();
        formPassword.blur();
        formPasswords.blur();
        submitForm()
    }
});

function submitForm() {
    if (flagUsername && flagEmail && flagPasswords) {
        register(formUsername.val().trim(), formEmail.val().trim(), formPassword.val().trim())
    } else {
        if (!flagUsername) {
            formUsername.removeClass("is-valid");
            formUsername.addClass("is-invalid")
        }
        if (!flagEmail) {
            formEmail.removeClass("is-valid");
            formEmail.addClass("is-invalid")
        }
        if (!flagPassword) {
            formPassword.removeClass("is-valid");
            formPassword.addClass("is-invalid")
        }
        if (!flagPasswords && flagPassword) {
            formPasswords.removeClass("is-valid");
            formPasswords.addClass("is-invalid")
        }
    }
}

function justUsername(username) {
    $.ajax({
        url: "/user/username/" + username,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                $("#usernameFeedback").html(result.message);
                formUsername.removeClass("is-valid");
                formUsername.addClass("is-invalid");
                flagUsername = false
            } else {
                formUsername.removeClass("is-invalid");
                formUsername.addClass("is-valid");
                flagUsername = true
            }
        }
    })
}

function justEmail(email) {
    $.ajax({
        url: "/user/email/" + email,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                $("#emailFeedback").html(result.message);
                formEmail.removeClass("is-valid");
                formEmail.addClass("is-invalid");
                flagEmail = false
            } else {
                formEmail.removeClass("is-invalid");
                formEmail.addClass("is-valid");
                flagEmail = true
            }
        }
    })
}

function register(username, email, password) {
    $.ajax({
        url: "/user/register",
        type: "post",
        data: {
            username: username,
            email: email,
            password: password
        },
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("#tooltip-msg").html(result.message);
                $("#tooltip-open").click();
                setTimeout(function () {
                    window.location.href = "/admin/login"
                }, 2000)
            } else {
                formUsername.removeClass("is-valid");
                formEmail.removeClass("is-valid");
                formCheckCode.removeClass("is-valid");
                formPassword.removeClass("is-valid");
                formPasswords.removeClass("is-valid");
                $("#register-form")[0].reset();
                $("#tooltip-msg").html(result.message);
                $("#tooltip-open").click()
            }
        }
    })
}
