<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$l = $lesson;
$minutes = strtotime($l->eventDate) - strtotime(date("Ymdhms"));
$minutes = ($minutes / 100) / 24;
?>
<section class="main-content">
    <div class="">
        <h2  class="lessonName"><?php echo ucfirst($l->name) ?></h2>
        <div id="activeLesson" class="activeLesson">The Lesson is now Active! If you are the Expert hobbyist, you can now start your lesson.<br/>
        If you are a Novice, please check back when the expert hobbyist starts the lesson!</div>
                <?php
if ($minutes <= -2.1) {
    echo "&nbsp;<p class='inactive'><i>(Inactive)</i></p>";
}
?>
           <img class="mainlessontype" src="/hobbyhorse/images/lessontype/<?php echo $l->lessonTypeId ?>.jpg" />


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
        <p><?php echo $l->description ?></p>

          <input type="button" id="startLesson" value="Start Lesson" onclick="startLesson('<?php echo $l->sessionId ?>');showEndLessonButton('<?php echo $l->createdBy ?>','<?php echo $_SESSION['user']->username ?>');updateLessonObject('<?php echo $l->id ?>','1');updateWasAttended('<?php echo $l->id ?>')"/>
    </div>
    <div id="myPublisherDiv"></div>
    <div id="endLesson"></div>

    <section class="comments">
        <h3 class="commentlabel">Comments:</h3>
        <div id="commentsDiv"><?php
            foreach ($comments->data as $comment) {
                echo "<div>";
                echo "<li>" . $comment->description . "</li>";
                echo "<span class='rating'>";
                for ($i = 0; $i < $comment->rating; $i++) {
        ?>
                    <img src="images/gold_star.jpeg" />
                    <?php
                }
                for ($i = 0; $i < 5 - $comment->rating; $i++) {
                    ?>
                    <img src="images/white_star.jpeg" />
                    <?php
                }
                echo "</span>";
                echo "<p class='createdBy'>Posted By $comment->createdBy on " . date("M d, Y", strtotime($comment->createDate)) . "</p>";
                echo "</div>";
            }
            ?></div>
        <p>
            <input type="hidden" id="lessonId" value="<?php echo $l->id ?>" />
            <input type="hidden" id="lessonName" value="<?php echo $l->name ?>" />
        <h4>Write your review:</h4>
        <textarea id="comment" name="comment" rows="5" cols="80"></textarea>
        <div class="rating"><br/>
            <img src="images/white_star.jpeg" id="img1" onClick="changeImage(this)"/>
            <img src="images/white_star.jpeg" id="img2" onClick="changeImage(this)"/>
            <img src="images/white_star.jpeg" id="img3" onClick="changeImage(this)"/>
            <img src="images/white_star.jpeg" id="img4" onClick="changeImage(this)"/>
            <img src="images/white_star.jpeg" id="img5" onClick="changeImage(this)"/>
        </div>
        <input type="button" value="Post!" id="post" onclick="postComment(this);" />
        </p>
    </section>
</section>
<script src="http://staging.tokbox.com/v0.91/js/TB.min.js"></script>
 
  <script type="text/javascript">
    // setInterval("alert(document.getElementsByTagName('object').length)",5000);
    checkCommentCanBePosted('<?php echo $l->eventDate . " " . $l->eventTime; ?>');
    setInterval("checkExpertHobbyistAndTimeOfLesson('<?php echo $l->userId ?>','<?php echo $_SESSION['user']->username ?>','<?php echo $l->eventDate . " " . $l->eventTime; ?>','<?php echo $l->isLive ?>')",2000);
    checkIfCommented('<?php echo $l->id ?>');
    function showEndLessonButton(lessonUser,sessionUser) {
        if(lessonUser==sessionUser) {
            document.getElementById('endLesson').innerHTML = "<a href=\"javascript:updateLessonObject('<?php echo $l->id ?>','0');\"><input type='button' name='closeLesson' value='End My Lesson'/></a>";
        }
        else {
            document.getElementById('endLesson').innerHTML = "<a href=\"javascript:(window.location.href=window.location.href);\"><input type='button' name='closeLesson' value='End My Lesson'/></a>";
        }
        setInterval("checkCommentCanBePosted('<?php echo $l->eventDate . " " . $l->eventTime; ?>')",10000);
    }

    function startLesson(sessionId) {
            var apiKey = '14098051';
            //var sessionId = '2_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTExIDA1OjAxOjA3LjYyMTM4MCswMDowMH4wLjc3OTU1NTAzMjQxOX4';
            var token = 'devtoken';          
             
            TB.setLogLevel(TB.DEBUG);    
         
            var session = TB.initSession(sessionId);     
            session.addEventListener('sessionConnected', sessionConnectedHandler);
            session.addEventListener('streamCreated', streamCreatedHandler);     
            session.connect(apiKey, token);
         
            var publisher;
         
            function sessionConnectedHandler(event) {
                  publisher = session.publish('myPublisherDiv');
                   
                  // Subscribe to streams that were in the session when we connected
                  subscribeToStreams(event.streams);
            }
             
            function streamCreatedHandler(event) {
                  // Subscribe to any new streams that are created
                  subscribeToStreams(event.streams);
            }
             
            function subscribeToStreams(streams) {
                  for (var i = 0; i < streams.length; i++) {
                        // Make sure we don't subscribe to ourself
                        if (streams[i].connection.connectionId == session.connection.connectionId) {
                              return;
                        }
                 
                        // Create the div to put the subscriber element in to
                        var div = document.createElement('div');
                        div.setAttribute('id', 'stream' + streams[i].streamId);
                        document.body.appendChild(div);
                                           
                        // Subscribe to the stream
                        session.subscribe(streams[i], div.id);
                  }
            }
    }
</script>

