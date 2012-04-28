<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <?php echo "<h2> Available Lessons</h2>" ?>
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
                <?php if ($joined[$l->id] != 0) { ?>
                    <h4 class="lessonName"><a href="<?php echo base_url() ?>index.php/lesson/main/<?php echo $l->id ?>"><?php echo $l->name ?></a></h4> 
                    <?php
                } else {
                    ?>
                    <h4 class="lessonName"><?php echo $l->name ?></h4> 
                   <?php 
                   }
                ?>
                <h5 class="expertname">The Expert: <?php
                if ($l->username == $_SESSION['user']->name) {
                    echo "Me !";
                } else {
                    echo $l->username;
                }
                ?></h5>
                <p><?php
                echo "Starts on: " . date("M d, Y", strtotime($l->eventDate));
                echo " at ";
                echo $l->eventTime
                ?></p>
                <p><?php echo $l->description ?></p>
                <?php if ($joined[$l->id] == 0) { ?>
                    <input type="button" name="joinLesson" id="joinLesson" value="Join" onclick="joinLesson(<?php echo $l->id .",'". $l->name."'" ?>);disableElement(this)"/>
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

