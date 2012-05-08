<div class="article">

    <h2>What is Hobbyhorse?</h2>
    <div class="clr"></div>

    <img src="images/tp_bg2.jpg" width="201" height="146" alt="" class="fl" />
    <p>Various social networking sites have become a prominent part of life of the current generation. However, apart from the status updates and pictures of the friends across the network, there is not much that one learns from it. In view of this scenario and access of such rich and large user data, we have developed a web-based / mobile social data-mining project named Hobby Horse (Social Learning). This system is for the users who would  like to impart their talents related to their hobbies in the form of 
       teaching online (real-time) and for the users who wish to pursue a hobby of their choice at their leisure. 
    </p>
    <p>Users of the system can have a role of ‘expert hobbyist’, a ‘novice hobbyist’ or both. Users can participate in different learning categories, or can create one dynamically. Hobbyists will be rewarded points and ratings to define their badge-levels or skill-levels based on the feedbacks. The ratings or badge levels will be used to recommend them to each other. They can then redeem their points towards free gifts or coupons.</p>
 </div>

<div id="popupContact">
    <a id="popupContactClose">x</a>
    <h1>Create account with hobbyhorse!</h1><br/><br/><br/>
    <h4>Fields marked with <span class="required">*</span> are compulsary fields.</h4>
    <div class="clr"></div>
    <div id="contactArea">
        <form id="login" action="/hobbyhorse/index.php/user/saveUser" method="POST">
            <input type="hidden" name="submit" value="1" />
            <div class="form">
                <ul class="form_elements">
                    <li class="label">First Name:<span class="required">*</span></li>
                    <li class="text_box"><input type="text" name="firstName" onfocus="checkRequired(this,'firstname')"  class="req" onkeyup="checkRequired(this,'firstname')"/><span class="nodisplay required" id="firstname">&nbsp;Required Field</span></li>
                    <li class="label">Last Name:</li>
                    <li class="text_box"><input type="text" name="lastName"  class="req" onkeyup="checkRequired(this,'lastname')"/><span class="nodisplay required" id="lastname">&nbsp;Required Field</span></li>
                    <li class="label">User Name:<span class="required">*</span></li>
                    <li class="text_box"><input type="text" name="username"  class="req" onkeyup="checkRequired(this,'usern')"/><span class="nodisplay required" id="usern">&nbsp;Required Field</span></li>
                    <li class="label">Email:<span class="required">*</span></li>
                    <li class="text_box"><input type="text" name="email"  class="req" onkeyup="checkRequired(this,'email')"/><span class="nodisplay required" id="email">&nbsp;Required Field</span></li>
                    <li class="label">Security Question:<span class="required">*</span></li>
                    <li class="text_box">
                        <select name="securityQuestion" class="securityQuestion">
                            <option value="cityBorn" id="ques1">Which city were you born in?</option>
                            <option value="firstTeacher" id="ques2">What is the name of your first teacher?</option>
                            <option value="motherMaiden" id="ques3">What is your mother's maiden name?</option>
                            <option value="favoriteColor" id="ques4">Which is your favorite color?</option>
                        </select>
                    </li>
                    <li class="label">Answer:<span class="required">*</span></li>
                    <li class="text_box"><input type="text" name="answer"  class="req" onkeyup="checkRequired(this,'answer')"/><span class="nodisplay required" id="answer">&nbsp;Required Field</span></li>
                    <li class="label">Password:<span class="required">*</span></li>
                    <li class="text_box"><input type="password" name="password" id="checkpass"  class="req" onkeyup="checkRequired(this,'pass')"/><span class="nodisplay required" id="pass">&nbsp;Required Field</span></li>
                    <li class="label">Confirm password:<span class="required">*</span></li>
                    <li class="text_box"><input type="password" name="confirmPassword" id="confpasscheck" onkeyup="checkIfMatch(this,'checkconf')"/><span class="nodisplay required" id="checkconf">&nbsp;Passwords do not match!</span><span class="nodisplay required" id="confirmpass">&nbsp;Required Field</span></li>
                    <li class="label">DOB:</li>
                    <li class="text_box"><input type="text" name="dob"/></li>
                    <li class="label">City:</li>
                    <li class="text_box"><input type="text" name="city"/></li>
                    <li class="label">Skill Sets:<span class="required">*</span> (Please enter comma separated values)</li>
                    <li class="text_box"><textarea  class="req" name="skills"></textarea></li>
                    <li class="label">Hobbies:<span class="required">*</span>(Please enter comma separated values)</li>
                    <li class="text_box"><textarea  class="req" name="hobbies"></textarea></li>
                    <li class="label">Image:</li>
                    <li class="text_box"><input type="text" name="image"/></li>
                </ul>
            </div>
            <div class="submitButton">
                <input type="submit" name="Save" value="Create My Account!" onclick="return validatePage();"/>
            </div>
        </form>

    </div>
</div>

<div id="backgroundPopup"></div>

