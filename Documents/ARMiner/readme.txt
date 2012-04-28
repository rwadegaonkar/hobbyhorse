readme.txt - December 5th, 2001 - by Laurentiu Cristofor

Welcome to the distribution of ARMiner, a client-server application
for finding association rules in datasets. ARMiner is distributed
under the GNU General Public License, whose text is included in this
distribution.

The arminer.tar.gz file contains the ARMiner distribution (binaries,
sources, and documentation) and is organized as follows:

copyright.txt          - copyright disclaimer
gpl.txt                - The GNU General Public License
readme.txt             - this file
bin/Client/            - ARMiner client binaries
bin/Server/            - ARMiner server binaries
manuals/               - ARMiner manuals
src/client/            - ARMiner client specific sources
src/server/            - ARMiner server specific sources
src/common/            - ARMiner sources needed by both client and 
                         server

To compile ARMiner, copy src/common in src/client/ and src/server/,
then run 'make' in src/server/ and 'make allClient' in src/client/. You
need Java 1.2 or later and a make utility. If you do not have a make
utility you can figure out how to compile the sources by taking a look
at the contents of the makefile. You can generate a documentation for
the server sources by running command 'javadoc
src/server/*.java'. ARMiner does not run or compile if you have a Java
version earlier than 1.2.

We recommend that you browse through the documentation before
launching ARMiner. See the client manual first for an introduction of
the user interface.

For a quick start, first launch the server:

java -jar Server.jar

and then launch the client application:

java -jar Client.jar

You can login as admin with password renimra. 

If during your experiments you run out of memory then you can rerun
ARMiner and allocate more memory to the JVM using the -Xmx command
line option, as shown below:

java -Xmx256M -jar Server.jar

java -Xmx256M -jar Client.jar

For questions, comments, send email to Laurentiu Cristofor
(laur@cs.umb.edu).


Changes history
===============

December 5th, 2001
~~~~~~~~~~~~~~~~~~

Server 1.0a   - I have fixed an error in the Itemset.add() method
              - I have fixed RandomPoissonDistribution to approximate
                a Poisson distribution with large mean by a normal
                distribution. This fixes the infinite loop that
                occurred in the synthetic data generation if one
                selected large values for the average pattern size.


April 5th, 2001
~~~~~~~~~~~~~~~

Server 1.0    - I have fixed the HashTree data structure to handle
                large numbers of itemsets.
              - I have replaced the quicksort algorithm used for
                sorting the mining results with a heapsort that is
                faster and uses less memory.
              - I have changed the maximum number of results sent to
                the client in one batch to 5000.
              - The server now displays a short message telling how
                many rules resulted from the mining.
              - I made some minor changes in error messages and
                comments.

Client 1.0b   - I have added a SaveAll button to the mining results
                dialog to enable the user to save all the results and
                not only the portion that has been requested from the
                server via the Next button. Note however that if there
                are tens of thousands of rules it will take some time
                until all the results are transferred.
              - I have changed the format in which the rules are saved
                by adding a rule number.


November 30th, 2000
~~~~~~~~~~~~~~~~~~~

Server 0.9b   - I have fixed an error in the rule generation procedure
                which made it generate fewer rules than it should.


November 22nd, 2000
~~~~~~~~~~~~~~~~~~~

Server 0.9a   - the synthetic data generator has been slightly modified;
                the current version should be better from a statistical
                point of view but it may be hard to see the difference

Client 1.0a   - Mining result dialog can be resized and the table will
                resize too allowing better display of rules
              - Mined rules are saved in a format that is much more
                readable
              - Tables no longer allow swapping of columns
              - Replaced the user and group management dialogs with a 
                secondary version made by Li so that the lists behave 
                in a more helpful way now
              - Fixed a bug in the user management dialog that prevented
                changing passwords
              - Several bug fixes in error handling code
              - Keyboard accelerators have been now added for all menus
                and buttons of main dialogs
              - Text for labels, dialog titles, buttons has been changed
                to be more explicit
              - Buttons and other controls have been resized and replaced
                so that dialogs share more of a common look
              - Labels no longer use different fonts and colors for 
                different dialogs
              - Client sources have been cleaned up and reorganized, better
                names have been given for classes so that now it is easier
                to find one's way around in the client sources
              - Debugging code has been commented so it doesn't clutter
                the screen anymore

Maintenance logs have been set in all files modified, detailing the
changes made, for more information look at the sources.


June 22nd, 2000
~~~~~~~~~~~~~~~

Server 0.9    - initial release

Client ?.?    - initial release, no version given
