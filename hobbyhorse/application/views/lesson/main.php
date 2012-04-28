<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$l = $lesson;
?>
<section class="main-content">
    <div class="">
        <h2  class="lessonName"><?php echo $l->name ?></h2> 
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

          <input type="button" value="Start Lesson" onclick="startLesson('<?php echo $l->sessionId ?>');showEndLessonButton();"/>
    </div>
    <div id="myPublisherDiv"></div>
    <div id="endLesson"></div>
    
<section class="comments">
    Comments:
    <p>
        <input type="hidden" id="lessonId" value="<?php echo $l->id?>" />
        <input type="hidden" id="lessonName" value="<?php echo $l->name?>" />
        <textarea id="comment" name="comment"></textarea>
        <select id="rating" name="rating">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
        <input type="button" value="Post!" id="post" onclick="postComment();disableElement(this)" />
    </p>
</section>
</section>
<script src="http://staging.tokbox.com/v0.91/js/TB.min.js"></script>
 
  <script type="text/javascript">
    checkIfCommented('<?php echo $l->id?>');
    function showEndLessonButton() {
        document.getElementById('endLesson').innerHTML = "<a href='<?php echo base_url() ?>index.php/lesson/main/<?php echo $l->id ?>'><input type='button' name='closeLesson' value='End My Lesson'/></a>";
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

