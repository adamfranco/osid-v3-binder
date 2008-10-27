<?php

requireAllInDir(dirname(__FILE__).'/../src/org/osid');

function requireAllInDir ($dir) {
	foreach (scandir($dir) as $file) {
		if (is_dir($dir.'/'.$file) && $file != '.' && $file != '..')
			requireAllInDir($dir.'/'.$file);
		else if (preg_match('/.+\.php$/', $file))
			require_once($dir.'/'.$file);
			
	}
}