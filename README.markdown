# Mogwee Logging, a simple wrapper around Log4J

Mogwee Logging is just a bunch of methods to make using Log4J nicer.


## Maven Info

	<dependency>
		<groupId>com.mogwee</groupId>
		<artifactId>mogwee-logging</artifactId>
		<version>1.0.0</version>
	</dependency>


## Usage

To construct a logger, add a static member variable to your class:
	private static final Logger LOG = Logger.getLogger();

Logger.getLogger() does some magic to determine the calling class.  (Specifically, it throws and catches an exception, then walks the stack trace.)  As this is not the sort of thing you want (or need) to be doing hundreds of times a second, it will log a warning if it looks like it's not being called from a static constructor.

Once you have a logger, there are four logging levels: debug, info, warn, and error.  For each level, there are four ways to log:
	1. constant message
		LOG.debug("My message");
		LOG.warn("My message");
		LOG.info("My message");
		LOG.error("My message");
	2. constant message with cause stack trace
		LOG.debug(e, "My message");
		LOG.warn(e, "My message");
		LOG.info(e, "My message");
		LOG.error(e, "My message");
	3. formatted message:
		LOG.debugf("My message: %s", message);
		LOG.warnf("My message: %s", message);
		LOG.infof("My message: %s", message);
		LOG.errorf("My message: %s", message);
	4. formatted message with cause stack trace
		LOG.debugf(e, "My message: %s", message);
		LOG.warnf(e, "My message: %s", message);
		LOG.infof(e, "My message: %s", message);
		LOG.errorf(e, "My message: %s", message);

For formatting, String.format() is used under the covers (but with enough smarts to not call it if the logging level isn't enabled).

There are also a couple more variants for the info, warn, and error levels:
	1. constant message with cause stack trace logged only if debug is enabled
		LOG.warnDebug(e, "My message");
		LOG.infoDebug(e, "My message");
		LOG.errorDebug(e, "My message");
	2. formatted message with cause stack trace logged only if debug is enabled
		LOG.warnDebugf(e, "My message: %s", message);
		LOG.infoDebugf(e, "My message: %s", message);
		LOG.errorDebugf(e, "My message: %s", message);


== Dependencies

Mogwee Logging depends on Log4J, which is available in pretty much every Maven repository.


== Build

    mvn install


== License (see COPYING file for full license)

Copyright 2011 Ning, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.