<div class="article">

    <h2>What is Hobbyhorse?</h2>
    <div class="clr"></div>

    <img src="images/website/img1.jpg" width="201" height="146" alt="" class="fl" />
    <p>This is a free CSS website template by AtomicWebsiteTemplates.com. This work is distributed under the Creative Commons Attribution 3.0 License, which means that you are free to use it for any personal or commercial purpose provided the credit links in the template footer are left intact.</p>
    <p>Aenean consequat porttitor adipiscing. Nam pellentesque justo ut tortor congue lobortis. </p>
 </div>

<div id="popupContact">
    <a id="popupContactClose">x</a>
    <h1>Create account with hobbyhorse!</h1>
    <div class="clr"></div>
    <div id="contactArea">
        <form id="login" action="/hobbyhorse/index.php/user/saveUser" method="POST">
            <input type="hidden" name="submit" value="1" />
            <div class="form">
                <ul class="form_elements">
                    <li class="label">First Name:</li>
                    <li class="text_box"><input type="text" name="firstName"/></li>
                    <li class="label">Last Name:</li>
                    <li class="text_box"><input type="text" name="lastName"/></li>
                    <li class="label">User Name:</li>
                    <li class="text_box"><input type="text" name="username"/></li>
                    <li class="label">Email:</li>
                    <li class="text_box"><input type="text" name="email"/></li>
                    <li class="label">Security Question:</li>
                    <li class="text_box">
                        <select name="securityQuestion" class="securityQuestion">
                            <option value="cityBorn" id="ques1">Which city were you born in?</option>
                            <option value="firstTeacher" id="ques2">What is the name of your first teacher?</option>
                            <option value="motherMaiden" id="ques3">What is your mother's maiden name?</option>
                            <option value="favoriteColor" id="ques4">Which is your favorite color?</option>
                        </select>
                    </li>
                    <li class="label">Answer:</li>
                    <li class="text_box"><input type="text" name="answer"/></li>
                    <li class="label">Password:</li>
                    <li class="text_box"><input type="password" name="password"/></li>
                    <li class="label">Confirm password:</li>
                    <li class="text_box"><input type="password" name="confirmPassword"/></li>
                    <li class="label">DOB:</li>
                    <li class="text_box"><input type="text" name="dob"/></li>
                    <li class="label">City:</li>
                    <li class="text_box"><input type="text" name="city"/></li>
                    <li class="label">Skill Sets:</li>
                    <li class="text_box"><textarea name="skills"></textarea></li>
                    <li class="label">Hobbies:</li>
                    <li class="text_box"><textarea name="hobbies"></textarea></li>
                    <li class="label">Image:</li>
                    <li class="text_box"><input type="text" name="image"/></li>
                </ul>
            </div>
            <div class="submitButton">
                <input type="submit" name="Save" value="Create My Account!" />
            </div>
        </form>

    </div>
</div>

<div id="backgroundPopup"></div>

