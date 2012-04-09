<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <div>
        <p>Available Lesson Categories:            
            <select id="lessonCategories" onChange="getLessonByTypes()">

                <?php
                foreach ($lessonTypes->data as $lessontype) {
                    ?>
                    <option value="<?php echo $lessontype->id ?>"><?php echo $lessontype->name ?></option>
                <?php } ?>
            </select>
        </p>
    </div>
    <section id="lessonList">
    <?php
    foreach ($lessons->data as $l) {
        ?>
        <div class="grid_5">
            <h4><?php echo $l->name ?></h4>
            <p><?php echo $l->description ?></p>
        </div>
        <?php
    }
    ?>
    </section>
</section>

