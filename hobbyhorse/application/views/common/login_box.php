<?php if(isset ($error)) {
        echo "<span class='error'>".$error."</span>";
    }
    ?>
<div id="login-box">
    <form action="<?php echo base_url()?>index.php/user/checkLogin" method="post">
        <div>
            <input type="text" placeholder="Username" required="" id="username" name="username" />
        </div>
        <div>
            <input type="password" placeholder="Password" required="" id="password" name="password" />
        </div>

        <div>
            <input type="submit" value="Log in" />
            <a href="#">Forgot password?</a>
            <a href="#" id="register" onclick="return false">Register</a>
        </div>
    </form>


</div>


