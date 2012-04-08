<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
echo "<pre>";
print_r($lessons->data);
foreach($lessons->data as $l) {
    ?>
<div class="block">
    <p><?php $l->name ?></p>
</div>
<?php
}
?>
