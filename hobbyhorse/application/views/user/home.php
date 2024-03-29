<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>    
<section>
    <?php echo "<h2>Welcome, " . ucfirst($user->name) . "</h2>" ?>
    <?php
    $flag = false;
    $uniqueLesson = array();
    $suggested_lessons = $_SESSION['suggested_lessons'];
    foreach ($suggested_lessons as $lessons) {
        if (!empty($lessons)) {
            foreach ($lessons as $l) {
                if (!empty($l)) {
                    $uniqueLesson["$l->id"] = $l;
                    $flag = true;
                }
            }
        }
    }
    if ($flag) {
        echo "<h3 class='suggestedH'><i>Some Recommended Lessons For You</i></h3>";
    }
    foreach ($uniqueLesson as $l) {
        ?>
        <div class="div2">
            <img class="descImg" src="images/lessontype/<?php echo $l->lessonTypeId ?>.jpg" />
            <h4 class="lessonName"><?php echo ucfirst($l->name) ?></h4>
            <h5 class="expertname">The Expert: <?php
            if ($l->username == $_SESSION['user']->name) {
                echo "<a href='" . base_url() . "index.php/lesson/expertLessons/" . $l->userId . "'>Me</a>";
            } else {
                echo "<a href='" . base_url() . "index.php/lesson/expertLessons/" . $l->userId . "'>$l->username</a>";
            }
                ?></h5>
            <p><?php
            echo "Starts on: " . date("M d, Y", strtotime($l->eventDate));
            echo " at ";
            echo $l->eventTime
        ?></p>
            <p><?php echo $l->description; ?>
                <?php if ($l->rating > 0) { ?>
                    }
                <div class="rating">
                    Rating for the Expert:<br/>
                    <?php
                    for ($i = 0; $i < $l->rating; $i++) {
                        ?>
                        <img src="images/gold_star.jpeg" />
                        <?php
                    }
                    ?>
                </div>
            <?php } ?>
            </p>
            <input type="button" name="joinLesson" id="joinLesson" value="Join" onclick="joinLesson(<?php echo $l->id ?>, '<?php echo $l->name ?>');disableElement(this)"/>

        </div>
        <?php
    }
    ?>
</section>
<div class="clr"></div>
<section>
    <div>
        <a href="<?php echo base_url() ?>index.php/lesson">Browse All Lessons!</a>
    </div>
</section>
