<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<?=base_url();?>">
    <title><?=$title?></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="css/website-home.css" rel="stylesheet" type="text/css" />
    <link href="css/popup.css" rel="stylesheet" type="text/css" />
    <link href="css/main.css" rel="stylesheet" type="text/css" />

    <?php
    if(isset($css))
    {
        if(!is_array($css)) $css = array($css);
        foreach($css as $c)
        {
            echo '<link href="css/'. $c .'" rel="stylesheet" type="text/css" />';
        }
    }
    ?>
    <script type="text/javascript" src="js/cufon-yui.js"></script>
    <script type="text/javascript" src="js/arial.js"></script>
    <script type="text/javascript" src="js/cuf_run.js"></script>
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.min.js"></script>
    <script type="text/javascript" src="js/radius.js"></script>
    <script type="text/javascript" src="js/popup.js"></script>
    <script type="text/javascript" src="js/script.js"></script>

    <?php
    if(isset($js))
    {
        if(!is_array($js)) $js = array($js);
        foreach($js as $j)
        {
            echo '<script type="text/javascript" src="js/'. $j .'"></script>';
        }
    }
    ?>
</head>
<body>
<!-- START PAGE SOURCE -->
<div class="main">
    <div class="header">
        <div class="header_resize">
            <div class="menu_nav">
                <ul>
                </ul>
            </div>
            <div class="logo">
                <div id="logo-img" style="float: left;margin-top: 10px;">
                    <a href="<?=base_url()?>"><img src="images/website/fbg_img.jpg" width="112" height="68" alt="" border="0" style="float:left;" /></a>
                </div>
                <h1><a href="<?=base_url()?>">Hobby<span>Horse</span> <small> | Learn More...</small></a></h1>
            </div>
            <div class="clr"></div>
            <a href="<?=$dialog_url?>"><img src="images/fb-sign-up-button.png" border="0" style="float:left; margin-top: 20px;" /></a>
            <div class="headertext">
                <h2>Login</h2>
                <?  if(isset($header_content))  echo $header_content;   ?>
            </div>
            <div style="clear:both;"></div>
        </div>
    </div>
    <div class="content">
        <div class="content_resize">
            <div class="mainbar">
                    <?  if(isset($content))  echo $content;   ?>
            </div>
            <div class="clr"></div>
        </div>
    </div>
    <div class="fbg">
        <div class="fbg_resize">
            <div class="col c1">
                <h2><span>Lesson Categories</span></h2>
                <a href="#"><img src="images/website/music.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/lessontype/1.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/lessontype/2.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/lessontype/3.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/website/pix5.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/website/pix6.jpg" width="58" height="58" alt="" /></a> </div>
            <div class="col c2">
                <h2><span>Social Learning</span></h2>
                <p>
                    Have you ever dreamed about exploring - learning about your hobby? your interest? Have you ever wished, you could play that guitar very well and impress your friends? <br/>
                    It's time to stop thinking and start learning. Yes, you can learn different lessons in vast categories from the people around the globe. Explore the new way of learning.
                    Social Learning!
                </p>
            </div>
            <div class="col c3">
                <h2><span>Contact</span></h2>
                <p>- Radhika Wadegaonkar<br/>- Sulagna Bal</p>
                <p><a href="#">radhika.wadegaonkar@gmail.com</a><br/><a href="#">sbal2050@gmail.com</a></p>
            </div>
            <div class="clr"></div>
        </div>
    </div>
    <div class="footer">
        <div class="footer_resize">
            <p class="lr">Copyright &copy; 2012 <a href="<?=base_url()?>">HobbyHorse</a> - All Rights Reserved</p>
            <div class="clr"></div>
        </div>
    </div>
</div>
<!-- END PAGE SOURCE -->
</body>
</html>