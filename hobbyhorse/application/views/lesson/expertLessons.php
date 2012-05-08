<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <?php echo "<h2> Expert Lessons By " . $lessons->lesson->username . "</h2>" ?>
    <?php if ($lessons->lesson->rating > 0) {
        ?>
        <div class="rating">
            <h3>Average Rating for the Expert:</h3>
            <?php
            for ($i = 0; $i < $lessons->lesson->rating; $i++) {
                ?>
                <img src="images/gold_star.jpeg" />
                <?php
            }
            ?>
        </div>
    <?php }
    ?>
    <section id="lessonList">
        <?php
        foreach ($lessons->data as $l) {
            $minutes = strtotime($l->eventDate) - strtotime(date("Ymdhms"));
            $minutes = ($minutes / 100) / 24;
            ?>
            <div class="div2">
                <?php if ($joined[$l->id] != 0) { ?>
                    <h4 class="lessonName"><a href="<?php echo base_url() ?>index.php/lesson/main/<?php echo $l->id ?>"><?php echo ucfirst($l->name) ?></a></h4> 
                    <?php
                } else {
                    ?>
                    <h4 class="lessonName"><?php echo ucfirst($l->name) ?></h4> 
                    <?php
                }
                ?><?php
                if ($minutes <= -2.1) {
                    echo "<span class='inactive'><i>(Inactive)</i></span>";
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
                <?php echo $l->description ?>


                <?php if ($joined[$l->id] == 0) { ?>
                    <input type="button" name="joinLesson" id="joinLesson" value="Join" onclick="joinLesson(<?php echo $l->id . ",'" . $l->name . "'" ?>);disableElement(this)"/>
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

