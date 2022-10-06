/**
 * gulp imports
 */
import {
  src,
  task,
  dest
} from 'gulp';

/**
 * typescript imports
 */
import {
  createProject
} from 'gulp-typescript'


/**
 * declarations
 */


/**
 * project: the project imported from tsconfig.json
 */
const project =
    createProject('tsconfig.json');


/**
 * compile everything under build:typescript into dist/es2019
 */
task('build:typescript', () => {
  return src('./src/main/typescript/**/*.ts')
      .pipe(project())
      .pipe(dest('./dist/es2019'))
});

/**
 * compile
 */

task('build:scss', () => {

});