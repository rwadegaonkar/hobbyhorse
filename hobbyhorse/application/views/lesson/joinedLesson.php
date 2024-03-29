<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <?php echo "<h2> My Lessons</h2>" ?>

    <section id="lessonList">
        <?php
        foreach ($lessons->data as $l) {
            $minutes = strtotime($l->eventDate) - strtotime(date("Ymdhms"));
            $minutes = ($minutes / 100) / 24;
            ?>
            <div class="div2">                                
                <img class="descImg" src="images/lessontype/<?php echo $l->lessonTypeId ?>.jpg" />
                <h4 class="lessonName"><a href="<?php echo base_url() ?>index.php/lesson/main/<?php echo $l->id ?>"><?php echo ucfirst($l->name) ?></a></h4> 
                <?php
                if ($minutes <= -2.1) {
                    echo "<span class='inactive'><i>(Inactive)</i></span>";
                }
                ?>
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
                <p><?php echo $l->description ?>
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
                        for ($i = 0; $i < 5 - $l->rating; $i++) {
                            ?>
                            <img src="images/white_star.jpeg" />
                            <?php
                        }
                        ?>
                    </div> <?php }
                    ?></p>
                  </div>
            <?php
        }
        ?>
    </section>
    <div class="clr"></div><br/>
    <section>
        <?php
        if (!empty($suggested_lessons)) {
            $flag = false;
            echo "<h3 class='suggestedH'><i>Some Recommended Lessons For You</i></h3>";
            foreach ($suggested_lessons as $suggestedLessonNameId => $suggestedLesson) {
                echo "<section>";
                $suggestedLessonNameIdArray = explode("^:^", $suggestedLessonNameId);
                ?>
                <?php
                $sugLeson = $suggestedLesson->lessons->data;
                if (count($sugLeson) > 0) {
                    foreach ($sugLeson as $l) {
                        if (!in_array($l, $lessons->data)) {
                            $flag = false;
                        } else {
                            $flag = true;
                        }
                    }
                    if ($flag) {
                        ?>
                        <p class="suggestedP">Users who joined <span class="lessonName"><a href="<?php echo base_url() ?>index.php/lesson/main/<?php echo $suggestedLessonNameIdArray[0] ?>"><?php echo ucfirst($suggestedLessonNameIdArray[1]) ?></a></span> 
                            also joined:</p>
                        <?php
                    }
                    foreach ($sugLeson as $l) {
                        if (!in_array($l, $lessons->data)) {
                            ?>
                            <div class="div2">
                                <img class="descImg" src="images/lessontype/<?php echo $l->lessonTypeId ?>.jpg" />
                                <h4 class="lessonName"><?php echo ucfirst($l->name) ?></h4>
                                    <h5 class="expertname">The Expert: <?php
            if ($l->username == $_SESSION['user']->name) {
                echo "<a href='".base_url()."index.php/lesson/expertLessons/".$l->userId."'>Me</a>";
            } else {
                echo "<a href='".base_url()."index.php/lesson/expertLessons/".$l->userId."'>$l->username</a>";
            }
                ?></h5>
            
                                <p><?php
                    echo "Starts on: " . date("M d, Y", strtotime($l->eventDate));
                    echo " at ";
                    echo $l->eventTime
                            ?></p>
                                <?php echo substr($l->description, 0, 50) . "...<br/>" ?>
                                <?php if ($l->rating > 0) {
                                    ?>
                                    <div class="rating">
                                        Rating for the Expert:<br/>
                                        <?php
                                        for ($i = 0; $i < $l->rating; $i++) {
                                            ?>
                                            <img src="images/gold_star.jpeg" />
                                            <?php
                                        }
                                        for ($i = 0; $i < 5 - $l->rating; $i++) {
                                            ?>
                                            <img src="images/white_star.jpeg" />
                                            <?php
                                        }
                                        ?>
                                    </div>
                                <?php } ?>

                                <input type="button" name="joinLesson" id="joinLesson" value="Join" onclick="joinLesson(<?php echo $l->id ?>, '<?php echo $l->name ?>');disableElement(this)"/>

                            </div>
                            <?php
                        }
                    }
                }
                echo "</section><div class='clr'></div>";
            }
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
