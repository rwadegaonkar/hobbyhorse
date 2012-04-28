<script src="http://staging.tokbox.com/v0.91/js/TB.min.js"></script>



  <script type="text/javascript">



function showEndLessonButton() {



document.getElementById('endLesson').innerHTML = "<a href=''><input type='button' name='closeLesson' value='End My Lesson'/></a>";



}



function startLesson(sessionId) {



var apiKey = '14098051';



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



<section class="main-content">



<section id="lessonList">



<div class="div2">



<h4 class="lessonName">Boxing</h4>



<h5 class="expertname">The Expert: Radhika Wadegaonkar Kulkarni</h5>



<p>Starts on: April 12, 2012 at 14:00</p>



<p>We will have one hour of boxing fun !!!</p>



<input type="button" value="Start Lesson" onclick="startLesson('1_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDA1OjQwOjI3LjU3MDY1NCswMDowMH4wLjY5NzM3MTc3ODQ3N34');showEndLessonButton();"/></div>



<div id="myPublisherDiv"></div>



<div id="endLesson"></div>



<div class="div2">



<h4 class="lessonName">Radhika</h4>



<h5 class="expertname">The Expert: Radhika Wadegaonkar Kulkarni</h5>



<p>Starts on: April 12, 2012 at 14:00</p>



<p>1 hour session</p>



<input type="button" value="Start Lesson" onclick="startLesson('1_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDA2OjE0OjA2LjE0OTY2MSswMDowMH4wLjY1MjkyNjE1NDI3MX4');showEndLessonButton();"/></div>



<div id="myPublisherDiv"></div>



<div id="endLesson"></div>



<div class="div2">



<h4 class="lessonName">Mandolin</h4>



<h5 class="expertname">The Expert: Radhika Wadegaonkar Kulkarni</h5>



<p>Starts on: 04/10/2012 at 17:00</p>



<p>We take this up, in 10minutes!</p>



<input type="button" value="Start Lesson" onclick="startLesson('1_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDE5OjU0OjA4Ljg1ODEzMyswMDowMH4wLjE5OTQxNzExMzAwMX4');showEndLessonButton();"/></div>



<div id="myPublisherDiv"></div>



<div id="endLesson"></div>



</section>



</section>