<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <base href="<?= base_url(); ?>">
            <title><?= $title ?></title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <link href="css/website-main.css" rel="stylesheet" type="text/css" />
            <link href="css/main.css" rel="stylesheet" type="text/css" />
            <?php
            if (isset($css)) {
                if (!is_array($css))
                    $css = array($css);
                foreach ($css as $c) {
                    echo '<link href="css/' . $c . '" rel="stylesheet" type="text/css" />';
                }
            }
            ?>
            <script type="text/javascript" src="js/cufon-yui.js"></script>
            <script type="text/javascript" src="js/arial.js"></script>
            <script type="text/javascript" src="js/cuf_run.js"></script>
            <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.18.min.js"></script>
            <script type="text/javascript" src="js/radius.js"></script>
            <script type="text/javascript" src="js/script.js"></script>

            <?php
            if (isset($js)) {
                if (!is_array($js))
                    $js = array($js);
                foreach ($js as $j) {
                    echo '<script type="text/javascript" src="js/' . $j . '"></script>';
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
                            <li class="active"><a href="<?= base_url() ?>">Home</a></li>
                            <li class=""><a href="user/profile">Profile</a></li>
                        </ul>
                    </div>
                    <div class="logo">
                        <div id="logo-img" style="float: left;margin-top: 10px;">
                            <a href="<?=base_url()?>"><img src="images/website/fbg_img.jpg" width="112" height="68" alt="" border="0" style="float:left;" /></a>
                        </div>
                        <h1><a href="<?=base_url()?>">Hobby<span>Horse</span> <small> | Learn More...</small></a></h1>
                    </div>
                    <div class="clr"></div>
                    <div style="clear:both;"></div>
                </div>
            </div>
            <div class="content">
                <div class="content_resize">
                    <div class="mainbar">
                        <div class="article">
<? if (isset($content))
    echo $content; ?>
                        </div>
                    </div>
                    <? if (isset($sidebar))
                        echo $sidebar; ?>
                    <div class="clr"></div>
                </div>
            </div>

            <div class="footer">
                <div class="footer_resize">
                    <p class="lr">Copyright &copy; 2012 <a href="<?= base_url() ?>">HobbyHorse</a> - All Rights Reserved</p>
                    <div class="clr"></div>
                </div>
            </div>
        </div>
        <!-- END PAGE SOURCE -->
    </body>
</html>