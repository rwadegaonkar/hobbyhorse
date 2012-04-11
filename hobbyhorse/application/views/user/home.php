<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
Hi Welcome <?php echo $user->name ?>

You like all this:<br/>
<?php
//print_r($likes);
foreach($likes->data as $like) {
echo "PAGE: ".$like->name." in the CATEGORY: ".$like->category;
echo "<br/>";
}
?>