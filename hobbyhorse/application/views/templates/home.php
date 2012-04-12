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
                <h1><a href="<?=base_url()?>">Hobby<span>Horse</span> <small> | Learn More...</small></a></h1>
            </div>
            <div class="clr"></div>
            <a href="<?=base_url()?>"><img src="images/website/fbg_img.jpg" width="337" height="204" alt="" border="0" style="float:left;" /></a>
            <div class="headertext">
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
                <h2><span>Image Gallery</span></h2>
                <a href="#"><img src="images/website/pix1.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/website/pix2.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/website/pix3.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/website/pix4.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/website/pix5.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="images/website/pix6.jpg" width="58" height="58" alt="" /></a> </div>
            <div class="col c2">
                <h2><span>Lorem Ipsum</span></h2>
                <p>Lorem ipsum dolor<br />
                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec libero. Suspendisse bibendum. Cras id urna. <a href="#">Morbi tincidunt, orci ac convallis aliquam</a>, lectus turpis varius lorem, eu posuere nunc justo tempus leo. Donec mattis, purus nec placerat bibendum, dui pede condimentum odio, ac blandit ante orci ut diam.</p>
            </div>
            <div class="col c3">
                <h2><span>Contact</span></h2>
                <p>Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue.</p>
                <p><a href="#">support@yoursite.com</a></p>
                <p>+1 (123) 444-5677<br />
                    +1 (123) 444-5678</p>
                <p>Address: 123 TemplateAccess Rd1</p>
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