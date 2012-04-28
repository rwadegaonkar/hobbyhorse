<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
require_once 'Platform/opentok/API_Config.php';
require_once 'Platform/opentok/OpenTokSDK.php';

$apiObj = new OpenTokSDK(API_Config::API_KEY, API_Config::API_SECRET);

$session = $apiObj->create_session($_SERVER["REMOTE_ADDR"]);
$sessionId = $session->getSessionId();
?>
<script>
    $(function() {
        $("#eventDate").datepicker();
        $("#eventDate").datepicker("option","dateFormat","yy-mm-dd");
    });
</script>
<section class="main-content">           
    <h4>Create your new Lesson ! Be the Expert Hobbyist!</h4>  
    <form name="create" id="createLesson" method="POST" action="<?php echo base_url(); ?>index.php/lesson/saveLesson">
        <div>
            <p>In Lesson Category:            
                <select id="lessonTypeId" name="lessonTypeId">

                    <?php
                    foreach ($lessonTypes->data as $lessontype) {
                        ?>
                        <option value="<?php echo $lessontype->id ?>"><?php echo $lessontype->name ?></option>
                    <?php } ?>
                </select>
            </p>
        </div>
        <div>
            <input type="hidden" value="<?php echo $sessionId ?>" name="sessionId"/>
            <p>Name: <input type="text" name="name" /></p>
            <p>Description: <textarea name="description" /></textarea></p>
            <p>Date: <input type="text" name="eventDate" id="eventDate"/></p>
            <p>Time: <input type="text" name="eventTime" /></p>
            <input type="submit" value="Create My Lesson!" />
        </div>       
    </form>
</section>
