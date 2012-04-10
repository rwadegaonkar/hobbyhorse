<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <div>
        <p>Available Lesson Categories:            
            <select id="lessonCategories" onChange="getLessonByTypes()">

                <?php
                foreach ($lessonTypes->data as $lessontype) {
                    ?>
                    <option value="<?php echo $lessontype->id ?>"><?php echo $lessontype->name ?></option>
                <?php } ?>
            </select>
        </p>
    </div>
    <section id="lessonList">
        <?php
        foreach ($lessons->data as $l) {
            ?>
            <div class="grid_5">
                <h4><?php echo $l->name ?></h4> 
                <h5 class="expertname"><?php echo $l->username ?></h5>
                <p><?php echo "Starts on: " . $l->createDate ?></p>
                <p><?php echo $l->description ?></p>
            </div>
            <?php
        }
        ?>
    </section>
    <div class="clr"></div>
    <section>
        <div>
            <a href="">Cannot Find Your Lesson Type? Create a new one here!</a>
        </div>
    </section>
    
  <script src="http://staging.tokbox.com/v0.91/js/TB.min.js"></script>
 
  <script type="text/javascript">
    var apiKey = '14098051';
    var sessionId = '14685d1ac5907f4a2814fed28294d3f797f34955';
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
  </script>
  <div id="myPublisherDiv"></div>

 
</section>

