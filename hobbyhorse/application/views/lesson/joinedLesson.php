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
                <h4 class="lessonName"><a href="<?php echo base_url()?>index.php/lesson/main/<?php echo $l->id?>"><?php echo $l->name ?></a></h4> 
                <h5 class="expertname">The Expert: <?php
        if ($l->username == $_SESSION['user']->name) {
            echo "Me !";
        } else {
            echo $l->username;
        }
            ?></h5>
                <p><?php
                echo "Starts on: " . date("M d, Y",strtotime($l->eventDate));
                echo " at ";
                echo $l->eventTime
            ?></p>
                <p><?php echo $l->description ?></p>
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
