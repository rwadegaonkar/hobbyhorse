<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <div>
        <p>Lesson Categories:            
            <select id="lessonCategories" onChange="getLessonByTypes();changeLessonTypeImage()">
                <?php
                foreach ($lessonTypes->data as $lessontype) {
                    ?>
                    <option value="<?php echo $lessontype->id ?>"><?php echo $lessontype->name ?></option>
                <?php } ?>
            </select>
        </p>
            <img class="lessontypeimg" id="lessontypeimg" src="<?php echo base_url() ?>images/lessontype/1.jpg" />
    </div>
    <section id="lessonList">
        <?php
        foreach ($lessons->data as $l) {
            ?>
            <div class="div2">
                <h4 class="lessonName"><?php echo $l->name ?></h4> 
                <h5 class="expertname">The Expert: <?php
                if ($l->username == $_SESSION['user']->name) {
                    echo "Me !";
                } else {
                    echo $l->username;
                }
            ?></h5>
                <p><?php
                echo "Starts on: " . $l->eventDate;
                echo " at ";
                echo $l->eventTime
                ?></p>
                <p><?php echo $l->description ?></p>
                <?php if ($joined[$l->id] == 0) { ?>
                    <input type="button" name="joinLesson" id="joinLesson" value="Join" onclick="joinLesson(<?php echo $l->id ?>);disableElement(this)"/>
                    <?php
                } else {
                    ?>
                    <input type="button" name="joinLesson" id="joinLesson" value="Join" disabled="true"/>

                    <?php
                }
                ?>
            </div>
            <?php
        }
        ?>
    </section>
    <div class="clr"></div>
    <section>
        <div>
            <a href="<?php echo base_url() ?>index.php/lesson/create">Cannot Find Your Lesson & Like to be an Expert Hobbyist? Create a new one here!</a>
        </div>
    </section>
</section>

