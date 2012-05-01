<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <?php echo "<h2> My Forthcoming Lessons</h2>" ?>

    <section id="lessonList">
        <?php
        foreach ($lessons->data as $l) {
            ?>
            <div class="div2">
                <h4 class="lessonName"><a href="<?php echo base_url() ?>index.php/lesson/main/<?php echo $l->id ?>"><?php echo ucfirst($l->name) ?></a></h4> 
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
                <p><?php echo $l->description ?>
                    <?php if ($l->rating > 0) {
                        ?>
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
                    </div> <?php }
                    ?></p>
                  </div>
            <?php
        }
        ?>
    </section>
    <div class="clr"></div>
    <section>
        <?php
        if(!empty($suggested_lessons)) {
            echo "<h3 class='suggestedH'><i>Some Recommended Lessons For You</i></h3>";
        }
        foreach ($suggested_lessons as $suggestedLessonNameId => $suggestedLesson) {
            echo "<section";
            $suggestedLessonNameIdArray = explode("^:^", $suggestedLessonNameId);
            ?>
            <?php if (count($suggestedLesson->lessons->data) > 0) {
                ?>
                <p class="suggestedP">Users who joined <span class="lessonName"><a href="<?php echo base_url() ?>index.php/lesson/main/<?php echo $suggestedLessonNameIdArray[0] ?>"><?php echo ucfirst($suggestedLessonNameIdArray[1]) ?></a></span> 
                    also joined:</p>
                <?php
                foreach ($suggestedLesson->lessons->data as $l) {
                    if (!in_array($l, $lessons->data)) {
                        ?>
                        <div class="div2">
                            <h4 class="lessonName"><?php echo ucfirst($l->name) ?></h4>
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

                            <input type="button" name="joinLesson" id="joinLesson" value="Join" onclick="joinLesson(<?php echo $l->id ?>, '<?php echo $l->name ?>');disableElement(this)"/>

                        </div>
                        <?php
                    }
                }
            }
            echo "</section><div class='clr'></div>";
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
