addEventListener("DOMContentLoaded", (event) => {
    // main password validation
    const password = document.getElementById("password-input");
    const passwordAlert = document.getElementById("password-alert");
    const requirements = document.querySelectorAll(".requirements");
    const registerButton = document.getElementById("regButton");
    let lengBoolean, bigLetterBoolean, numBoolean, specialCharBoolean, passwordVerifyBool, passwordSameBool;
    let leng = document.querySelector(".leng");
    let bigLetter = document.querySelector(".big-letter");
    let num = document.querySelector(".num");
    let specialChar = document.querySelector(".special-char");
    const specialChars = "!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?`~";
    const numbers = "0123456789";

    requirements.forEach((element) => element.classList.add("wrong"));

    password.addEventListener("focus", () => {
        passwordAlert.classList.remove("d-none");
        if (password.value.length > 0 && !password.classList.contains("is-valid")) {
            password.classList.add("is-invalid");
            passwordVerify();
        }
    });

    password.addEventListener("input", () => {
        let value = password.value;
        if (value === '') {
            lengBoolean = false;
            bigLetterBoolean = false;
            numBoolean = false;
            specialCharBoolean = false;
    
            password.classList.remove("is-valid", "is-invalid");
    
            requirements.forEach((element) => element.classList.add("wrong"));
            requirements.forEach((element) => element.classList.remove("good"));
            passwordAlert.classList.remove("alert-warning", "alert-success");
            passwordVerification.classList.remove("is-valid", "is-invalid");
            passwordSameBool = false;
            return;
        }
    
        if (value.length < 8) {
            lengBoolean = false;
        } else if (value.length > 7) {
            lengBoolean = true;
        }

        if (value.toLowerCase() == value) {
            bigLetterBoolean = false;
        } else {
            bigLetterBoolean = true;
        }

        numBoolean = false;
        for (let i = 0; i < value.length; i++) {
            for (let j = 0; j < numbers.length; j++) {
                if (value[i] == numbers[j]) {
                    numBoolean = true;
                }
            }
        }

        specialCharBoolean = false;
        for (let i = 0; i < value.length; i++) {
            for (let j = 0; j < specialChars.length; j++) {
                if (value[i] == specialChars[j]) {
                    specialCharBoolean = true;
                }
            }
        }

        if (lengBoolean == true && bigLetterBoolean == true && numBoolean == true && specialCharBoolean == true) {
            passwordVerifyBool = true;
            password.classList.remove("is-invalid");
            password.classList.add("is-valid");

            requirements.forEach((element) => {
                element.classList.remove("wrong");
                element.classList.add("good");
            });
            passwordAlert.classList.remove("alert-warning");
            passwordAlert.classList.add("alert-success");
        } else {
            passwordVerifyBool = false;
            password.classList.remove("is-valid");
            password.classList.add("is-invalid");

            passwordAlert.classList.add("alert-warning");
            passwordAlert.classList.remove("alert-success");

            if (lengBoolean == false) {
                leng.classList.add("wrong");
                leng.classList.remove("good");
            } else {
                leng.classList.add("good");
                leng.classList.remove("wrong");
            }

            if (bigLetterBoolean == false) {
                bigLetter.classList.add("wrong");
                bigLetter.classList.remove("good");
            } else {
                bigLetter.classList.add("good");
                bigLetter.classList.remove("wrong");
            }

            if (numBoolean == false) {
                num.classList.add("wrong");
                num.classList.remove("good");
            } else {
                num.classList.add("good");
                num.classList.remove("wrong");
            }

            if (specialCharBoolean == false) {
                specialChar.classList.add("wrong");
                specialChar.classList.remove("good");
            } else {
                specialChar.classList.add("good");
                specialChar.classList.remove("wrong");
            }
        }
        passwordVerify();
    });


    password.addEventListener("blur", () => {
        passwordAlert.classList.add("d-none");
    });


    // password verification
    const passwordVerification = document.getElementById("password2");
    passwordVerification.addEventListener("input", passwordVerify);
    passwordVerification.addEventListener("focus", passwordVerify);


    function passwordVerify() {
        let value1 = passwordVerification.value;
        let value2 = password.value;
        if (value2 === '') {
            passwordVerification.classList.remove("is-valid"); //no validation if password value is empty
            passwordVerification.classList.remove("is-invalid");
            passwordSameBool = false;
        } 
        else if (value1.length === 0) {
            passwordVerification.classList.remove("is-valid");
            passwordVerification.classList.remove("is-invalid");
            passwordSameBool = false; 

        } 
        else if (value1 === value2) {
            passwordVerification.classList.remove("is-invalid");
            passwordVerification.classList.add("is-valid");
            passwordSameBool = true;
        } else {
            passwordVerification.classList.remove("is-valid");
            passwordVerification.classList.add("is-invalid");
            passwordSameBool = false;
        }
        formVerify();
    }

    // register disable/enable
    function formVerify() {
        if (passwordVerifyBool == true && passwordSameBool == true) {
            registerButton.removeAttribute("disabled");
        } else {
            registerButton.disabled = true;
        }
    }

});
