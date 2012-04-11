<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$htmlContent = <<< HTML
        <section class="main-content">
            <section class="grid_5">
                <article>
                <a href="$dialog_url">Connect with Facebook</a>
                    <p class="left-login">Connect with Facebook</p>
                    <p class="left-login">or</p>
                    <p class="left-login">Login With Linked In</p>
                </article>
            </section>
            <section class="grid_1">
                <p>OR</p>
            </section>
            <section class="grid_6">
                <h3>Create an account with Hobby Horse Now! </h3>
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
            </section>
        </section>
HTML;
echo $htmlContent;
?>