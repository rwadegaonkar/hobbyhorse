<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
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
                  <input type="button" value="Start Lesson" onclick="startLesson('<?php echo $l->sessionId ?>');showEndLessonButton();"/></div>
            <div id="myPublisherDiv"></div>
            <div id="endLesson"></div>
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
<script src="http://staging.tokbox.com/v0.91/js/TB.min.js"></script>
 
  <script type="text/javascript">
    function showEndLessonButton() {
        document.getElementById('endLesson').innerHTML = "<a href='<?php echo base_url()?>index.php/lesson/joinedLesson'><input type='button' name='closeLesson' value='End My Lesson'/></a>";
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

