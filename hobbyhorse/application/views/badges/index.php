<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<section class="main-content">
    <div>
        <p>
            Badges are assigned if you have:
        </p>
        <table class="badgeDesc">
            <tbody>
                <tr>
                    <td class="label">Lessons Attended</td>
                    <td class="label">Badge Attained</td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>Foal</td>
                </tr>
                <tr>
                    <td>15</td>
                    <td>Weanling</td>
                </tr>
                <tr>
                    <td>25</td>
                    <td>Yearling</td>
                </tr>
                <tr>
                    <td>35</td>
                    <td>Stallion</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div>
        <p>You have attended: <span class="label"><?php echo count($lessons->data); ?> Lesson(s)
            </span></p>
        <?php
        if (count($lessons->data) > 4) {
            foreach ($badges->data as $badge) {
                if (count($lessons->data) > $badge->lessonCount) {
                    $badgeAttained = $badge->name;
                }
            }
        } else {
            $badgeAttained = "None !";
        }
        ?>
        <p>
            <?php if ($badgeAttained == "None !") { ?>
               Badges Attained: <span class="red"> <?php echo $badgeAttained ?></span>
                <br/><br/><a href="index.php/lesson/">Start Learning More Now! </a>
            <?php } else { ?>
                Badges Attained: <span class="badgeLevel"><?php echo $badgeAttained ?></span>
                <?php
            }
            ?>
        </p>
    </div>
</section>